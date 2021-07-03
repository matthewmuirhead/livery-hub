package com.codemaven.liveries.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.codemaven.generated.tables.pojos.Languages;
import com.codemaven.liveries.enums.NavBarZone;
import com.codemaven.liveries.lists.LanguageFieldsList;
import com.codemaven.liveries.model.ExtendedUser;
import com.codemaven.liveries.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public abstract class ServletBase
{
	protected static final String USER_SESSION_KEY = "Session_User";
	protected static final String LANGUAGE_SESSION_KEY = "Current_Language";

	@GetMapping
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		req.setAttribute("navbarzone", getNavBarZone());
		req.setAttribute("zones", NavBarZone.values());
		checkIfAdminArea(req);
		processRequest(req, resp);
	}

	@PostMapping
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.setAttribute("navbarzone", getNavBarZone());
		req.setAttribute("zones", NavBarZone.values());
		checkIfAdminArea(req);
		processRequest(req, resp);
	}

	private void checkIfAdminArea(HttpServletRequest req)
	{
		NavBarZone currentZone = getNavBarZone();
		boolean isAdminArea = currentZone == NavBarZone.ADMIN_LANGUAGES || currentZone == NavBarZone.ADMIN_SERIES
				|| currentZone == NavBarZone.ADMIN_USERS;
		req.setAttribute("isAdminArea", isAdminArea);
	}

	/**
	 * Entry point method. Override in sub-class
	 * 
	 * @param req
	 *            HttpServletRequest
	 * @param resp
	 *            HttpServletResponse
	 */
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		// This should be overridden by the sub class.
		log.error("ServletBase ProcessRequest(HttpServletRequest, HttpServletResponse) accessed directly!");
	}

	protected ExtendedUser getLoggedInUser(HttpServletRequest req) throws IllegalStateException
	{
		ExtendedUser user = null;
		Object sessionUser = getSessionValue(req, USER_SESSION_KEY);
		if (sessionUser instanceof ExtendedUser)
		{
			user = (ExtendedUser) sessionUser;
		}
		return user;
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
	protected void setSessionValue(HttpServletRequest req, String key, Object object) throws IllegalStateException
	{
		HttpSession session = req.getSession();
		session.setAttribute(key, object);
	}

	protected Object getSessionValue(HttpServletRequest req, String key) throws IllegalStateException
	{
		HttpSession session = req.getSession();
		return session.getAttribute(key);
	}

	protected String getParameterString(final ServletRequest req, final String key)
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

	protected boolean getParameterBoolean(final HttpServletRequest req, final String key)
	{
		boolean value = false;
		Object o = req.getParameter(key);
		if (o instanceof Boolean)
		{
			value = (boolean) o;
		}
		else if (o instanceof String)
		{
			value = StringUtil.strToBoolean((String) o);
		}
		return value;
	}

	protected String getCmd(final HttpServletRequest req)
	{
		String cmd = getParameterString(req, "cmd");
		if (log.isDebugEnabled() && !StringUtil.isNullOrEmpty(cmd))
		{
			log.debug("Doing cmd: " + cmd);
		}
		return cmd;
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
			req.getRequestDispatcher("/errors/general-error")
					.forward(req, resp);
		}
		catch (Exception e)
		{
			log.error("An error occured when trying to display an error!", e);
		}
	}

	/**
	 * Fetch language fields list
	 * 
	 * @param req
	 * @return LanguageFieldsList
	 */
	protected LanguageFieldsList getLanguageFieldsList(ServletRequest req)
	{
		return (LanguageFieldsList) req.getAttribute("languageFieldsList");
	}

	/**
	 * Fetch languages list
	 * 
	 * @param req
	 * @return Languages
	 */
	@SuppressWarnings("unchecked")
	protected List<Languages> getLanguages(ServletRequest req)
	{
		return (List<Languages>) req.getAttribute("languages");
	}

	/**
	 * Fetch current language from request
	 * 
	 * @param req
	 * @return Languages
	 */
	protected Languages getCurrentLanguage(HttpServletRequest req)
	{
		return (Languages) getSessionValue(req, LANGUAGE_SESSION_KEY);
	}
}
