package com.codemaven.events.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.codemaven.events.manager.enums.NavBarZone;
import com.codemaven.events.servlet.ServletBase;
import com.codemaven.events.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(1)
@Slf4j
public class EventConcierge extends ServletBase implements Filter
{

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException
	{
 		String url = ((HttpServletRequest) req).getRequestURI();
		String servletName = getServletNameFromUrl(url);
		if (StringUtil.isEqual("manager", getBaseServletNameFromUrl(url)))
		{
			if (checkIfManagerServletIsSupported(servletName))
			{
				log.info("Manager Servlet " + servletName + " Supported, continuing...");
				chain.doFilter(req, resp);
			}
			else
			{
				if (log.isDebugEnabled())
				{
					log.debug("Tried to access invalid manager servlet: " + servletName);
				}
				displayPage((HttpServletRequest) req, (HttpServletResponse) resp, "/404", true);
			}
		}
		else
		{
			if (checkIfServletIsSupported(servletName))
			{
				log.info("Servlet " + servletName + " Supported, continuing...");
				chain.doFilter(req, resp);
			}
			else if (isImageAccess(url))
			{
				chain.doFilter(req, resp);
			}
			else if (StringUtil.isEqual("/favicon.ico", url))
			{
				chain.doFilter(req, resp);
			}
			else
			{
				if (log.isDebugEnabled())
				{
					log.debug("Tried to access invalid servlet: " + servletName);
				}
				displayPage((HttpServletRequest) req, (HttpServletResponse) resp, "/404", true);
			}
		}
	}
	
	private String getBaseServletNameFromUrl(String url)
	{
		String serlvetName = "";
		int slashPos = url.lastIndexOf('/');
		if (slashPos > 0 && slashPos < url.length() - 1)
		{
			serlvetName = url.substring(1, slashPos);
		}
		return serlvetName;
	}
	
	private boolean checkIfManagerServletIsSupported(String servletName)
	{
		String[] supportedServletNames =
		
		{ "", "events", "hosts", "tracks", "teams", "cars" };
		return StringUtil.arrayContains(supportedServletNames, servletName);
	}
	
	private boolean checkIfServletIsSupported(String servletName)
	{
		String[] supportedServletNames =
		
		{ "", "user", "events", "leagues", "404" };
		return StringUtil.arrayContains(supportedServletNames, servletName);
	}
	
	protected boolean isImageAccess(String url)
	{
		boolean supported = false;
		String baseServletName = "";
		String[] urlParts = StringUtil.split(url, "/");
		if (urlParts.length > 1)
		{
			baseServletName = urlParts[1];
		}
		supported = StringUtil.isEqual("img", baseServletName);
		if (supported)
		{
			log.info("Image Access for file: " + getServletNameFromUrl(url));
		}
		else
		{
			supported = StringUtil.isEqual("css", baseServletName);
			if (supported)
			{
				log.info("CSS Access for file: " + getServletNameFromUrl(url));
			}
		}
		return supported;
	}

	@Override
	protected NavBarZone getNavBarZone()
	{
		return null;
	}
}
