package com.codemaven.manager.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.codemaven.manager.enums.NavBarZone;
import com.codemaven.manager.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public abstract class ServletBase
{
	@GetMapping
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		req.setAttribute("navbarzone", getNavBarZone());
		processRequest(req, resp);
	}

	@PostMapping
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.setAttribute("navbarzone", getNavBarZone());
		processRequest(req, resp);
	}

	/**
	 * Entry point method. Override in sub-class
	 * 
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 */
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		// This should be overridden by the sub class.
		log.error("ServletBase ProcessRequest(HttpServletRequest, HttpServletResponse) accessed directly!");
	}
	
	protected abstract NavBarZone getNavBarZone();

	/**
	 * Display a page by using a JSP
	 * 
	 * @param req
	 * @param resp
	 * @param strJSPPath
	 */
	protected void displayPage(HttpServletRequest req, HttpServletResponse resp, String strJSPPath)
	{
		displayPage(req, resp, strJSPPath, false);
	}

	/**
	 * Display a page by using a JSP
	 * 
	 * @param req
	 * @param resp
	 * @param jspPath
	 * @param redirect
	 */
	protected void displayPage(HttpServletRequest req, HttpServletResponse resp, String jspPath, boolean redirect)
	{
		try
		{
			if (redirect)
			{
				log.debug("Redirecting to: " + jspPath);
				resp.sendRedirect(jspPath);
				return;
			}
			log.debug("Displaying page: " + jspPath);
			req.getRequestDispatcher(jspPath)
					.forward(req, resp);
		}
		catch (ServletException e)
		{
			log.error("ServletException Error displaying JSP page: " + e.getMessage(), e);
		}
		catch (IOException e)
		{
			log.error("IOException Error displaying JSP page: " + e.getMessage(), e);
		}
	}

	/**
	 * Set a session value using the supplied key and object
	 * 
	 * @param req
	 * @param strKey
	 * @param objObject
	 */
	protected void setSessionValue(HttpServletRequest req, String key, Object object)
	{
		HttpSession session = req.getSession();
		session.setAttribute(key, object);
	}

	protected String getParameterString(final HttpServletRequest req, final String key)
	{
		String value = null;
		Object o = req.getParameter(key);
		if (o instanceof String)
		{
			value = (String) o;
		}
		return value;
	}
	
	protected int getParameterInt(final HttpServletRequest req, final String key)
	{
		int value = 0;
		Object o = req.getParameter(key);
		if (o instanceof Integer)
		{
			value = (int) o;
		}
		else if (o instanceof String)
		{
			value = StringUtil.strToInt((String) o);
		}
		return value;
	}
	
	protected String getCmd(final HttpServletRequest req)
	{
		return getParameterString(req, "cmd");
	}
	
	/**
	 * Parse the Servlet name from the url String
	 * 
	 * @param url
	 * @return
	 */
	protected String getServletNameFromUrl(String url)
	{
		String serlvetName = "";
		int slashPos = url.lastIndexOf('/');
		if (slashPos > -1 && slashPos < url.length() - 1)
		{
			serlvetName = url.substring(slashPos + 1);
		}
		return serlvetName;
	}

	/**
	 * Display an error
	 * 
	 * @param req
	 * @param resp
	 * @param message
	 */
	protected void displayError(ServletRequest req, ServletResponse resp, String message)
	{
		try
		{
			log.debug("Message: " + message);
			req.setAttribute("message", message);
			req.getRequestDispatcher("/errors/general-error").forward(req, resp);
		}
		catch (Exception e)
		{
			log.error("An error occured when trying to display an error!", e);
		}
	}
}
