package com.codemaven.liveries.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.generated.tables.pojos.Users;
import com.codemaven.liveries.db.ServiceFactory;
import com.codemaven.liveries.db.ServiceType;
import com.codemaven.liveries.db.service.UsersService;
import com.codemaven.liveries.enums.NavBarZone;
import com.codemaven.liveries.lists.LanguageFieldsList;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/register")
@Slf4j
public class RegisterServlet extends ServletBase
{
	private static final String JSP_PATH = "hub/users/register.jsp";
	private ServiceFactory serviceFactory;

	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		try
		{
			if (getLoggedInUser(req) == null)
			{
				doCreateUser(req, resp);
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

	private void doCreateUser(HttpServletRequest req, HttpServletResponse resp)
	{
		boolean submitted = getParameterBoolean(req, "submitted");
		if (!submitted)
		{
			req.setAttribute("title", "Register");
			displayPage(req, resp, JSP_PATH);
		}
		else
		{
			Users user = populateUser(req);
			if (validateUser(req, user))
			{
				boolean saved = serviceFactory.getInstance(ServiceType.USER, UsersService.class)
						.saveUser(user);
				if (saved)
				{
					setSessionValue(req, USER_SESSION_KEY, user);
					displayPage(req, resp, "/", true);
					return;
				}
				else
				{
					LanguageFieldsList languageFieldsList = getLanguageFieldsList(req);
					req.setAttribute("generalError",
							languageFieldsList.getTranslation("Your user could not be created at this time"));
				}
			}

			displayPage(req, resp, JSP_PATH);
		}
	}

	private boolean validateUser(HttpServletRequest req, Users user)
	{
		boolean valid = true;
		LanguageFieldsList languageFieldsList = getLanguageFieldsList(req);

		if (serviceFactory.getInstance(ServiceType.USER, UsersService.class).userExists(user.getUsername()))
		{
			valid = false;
			req.setAttribute("generalError", languageFieldsList.getTranslation("A user already exists with that username"));
		}
		return valid;
	}

	private Users populateUser(HttpServletRequest req)
	{
		String username = getParameterString(req, "username");
		String password = getParameterString(req, "password");
		Users user = new Users();
		user.setUsername(username);
		user.setPassword(password);
		user.setAdmin(false);
		return user;
	}

	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.REGISTER;
	}
}
