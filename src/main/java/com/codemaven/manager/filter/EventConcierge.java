package com.codemaven.manager.filter;

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

import com.codemaven.manager.servlet.ServletBase;
import com.codemaven.manager.util.StringUtil;

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
		if (checkIfServletIsSupported(servletName) || isImageAccess(url))
		{
			log.info("Servlet Supported, continuing...");
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
	
	private boolean checkIfServletIsSupported(String servletName)
	{
		String[] supportedServletNames =
		
		{ "", "event", "404" };
		return StringUtil.arrayContains(supportedServletNames, servletName);
	}
	
	protected boolean isImageAccess(String url)
	{
		String baseServletName = "";
		String[] urlParts = StringUtil.split(url, "/");
		if (urlParts.length > 1)
		{
			baseServletName = urlParts[1];
		}
		return StringUtil.isEqualLenient("img", baseServletName);
	}
}
