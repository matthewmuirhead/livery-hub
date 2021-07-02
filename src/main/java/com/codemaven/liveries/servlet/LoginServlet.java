package com.codemaven.liveries.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.liveries.db.ServiceFactory;
import com.codemaven.liveries.db.ServiceType;
import com.codemaven.liveries.db.service.UsersService;
import com.codemaven.liveries.manager.enums.NavBarZone;
import com.codemaven.liveries.manager.lists.LanguageFieldsList;
import com.codemaven.liveries.model.ExtendedUser;
import com.codemaven.liveries.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/login")
@Slf4j
public class LoginServlet extends ServletBase
{
	private static final String JSP_PATH = "hub/users/login.jsp";
	private ServiceFactory serviceFactory;

	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		try
		{
			if (getLoggedInUser(req) == null)
			{
				doLogin(req, resp);
			}
			else
			{
				displayPage(req, resp, "/", true);
			}
		}
		catch (Exception e)
		{
			log.error("Error processing cmd: " + e.getMessage(), e);
			displayError(req, resp, "Uh-Oh, something went wrong!");
		}
	}

	private void doLogin(HttpServletRequest req, HttpServletResponse resp)
	{
		boolean submitted = getParameterBoolean(req, "submitted");
		if (!submitted)
		{
			req.setAttribute("title", "Login");
			displayPage(req, resp, JSP_PATH);
		}
		else
		{
			String username = getParameterString(req, "username");
			ExtendedUser user = serviceFactory.getInstance(ServiceType.USER, UsersService.class)
					.fetchUserByUsername(username);
			if (user != null)
			{
				if (StringUtil.isEqual(user.getPassword(), getParameterString(req, "password")))
				{
					setSessionValue(req, USER_SESSION_KEY, user);
					displayPage(req, resp, "/", true);
					return;
				}
			}

			LanguageFieldsList languageFieldsList = getLanguageFieldsList(req);
			req.setAttribute("generalError",
					languageFieldsList.getTranslation("Your username/password combination is incorrect"));
			displayPage(req, resp, JSP_PATH);
		}
	}

	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.LOGIN;
	}
}
