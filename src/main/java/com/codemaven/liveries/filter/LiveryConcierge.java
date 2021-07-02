package com.codemaven.liveries.filter;

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

import com.codemaven.liveries.manager.enums.NavBarZone;
import com.codemaven.liveries.servlet.ServletBase;
import com.codemaven.liveries.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(1)
@Slf4j
public class LiveryConcierge extends ServletBase implements Filter
{
	private static final String REDIRECT_404 = "/404";
	
	private static final String FILE_EXTENSION_IMG = "img";
	private static final String FILE_EXTENSION_CSS = "css";
	
	private static final String[] SUPPORTED_SERVLETS = { "", "series", "login", "logout", "register", "404" };
	private static final String[] SUPPORTED_SERVLETS_ADMIN = { "users", "series" };
	
	private static final String ADMIN_BASE_SERVLET = "admin";

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException
	{
		String url = ((HttpServletRequest) req).getRequestURI();
		String servletName = getServletNameFromUrl(url);
		if (StringUtil.isEqual(ADMIN_BASE_SERVLET, getBaseServletNameFromUrl(url)))
		{
			if (checkIfAdminServletIsSupported(servletName))
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
				displayPage((HttpServletRequest) req, (HttpServletResponse) resp, REDIRECT_404, true);
			}
		}
		else
		{
			if (checkIfServletIsSupported(servletName))
			{
				log.info("Servlet " + servletName + " Supported, continuing...");
				chain.doFilter(req, resp);
			}
			else if (isImageAccess(url) || StringUtil.isEqual("/favicon.ico", url))
			{
				chain.doFilter(req, resp);
			}
			else
			{
				if (log.isDebugEnabled())
				{
					log.debug("Tried to access invalid servlet: " + servletName);
				}
				displayPage((HttpServletRequest) req, (HttpServletResponse) resp, REDIRECT_404, true);
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

	private boolean checkIfAdminServletIsSupported(String servletName)
	{
		return StringUtil.arrayContains(SUPPORTED_SERVLETS_ADMIN, servletName);
	}

	private boolean checkIfServletIsSupported(String servletName)
	{
		return StringUtil.arrayContains(SUPPORTED_SERVLETS, servletName);
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
		supported = StringUtil.isEqual(FILE_EXTENSION_IMG, baseServletName);
		if (supported)
		{
			log.info("Image Access for file: " + getServletNameFromUrl(url));
		}
		else
		{
			supported = StringUtil.isEqual(FILE_EXTENSION_CSS, baseServletName);
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
