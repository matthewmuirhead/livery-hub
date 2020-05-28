package com.codemaven.events.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.events.db.ServiceFactory;
import com.codemaven.events.db.ServiceType;
import com.codemaven.events.db.service.UsersService;
import com.codemaven.events.manager.enums.NavBarZone;
import com.codemaven.events.util.StringUtil;
import com.codemaven.generated.tables.pojos.Users;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/user")
@Slf4j
public class UserServlet extends ServletBase
{
	private static final String JSP_PATH = "user";
	private static final int BCRYPT_SALT_ROUNDS = 10;
	private ServiceFactory serviceFactory;

	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		try
		{
			doCmd(req, resp);
		}
		catch (Exception e)
		{
			log.error("Error processing cmd: " + e.getMessage(), e);
			displayError(req, resp, "Uh-Oh, something went wrong!");
		}
	}
	
	private void doCmd(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String cmd = getCmd(req);
		Users user = getLoggedInUser(req);
		if (user == null)
		{
			if (StringUtil.isNullOrEmpty(cmd) || StringUtil.isEqual(cmd, "login"))
			{
				doLogin(req, resp);
			}
			else if (StringUtil.isEqual(cmd, "save"))
			{
				doSave(req, resp, false);
			}
		}
		else
		{
			if (StringUtil.isNullOrEmpty(cmd) || StringUtil.isEqual(cmd, "view"))
			{
				doView(req, resp);
			}
			else if (StringUtil.isEqual(cmd, "logout"))
			{
				doLogout(req, resp);
			}
			else if (StringUtil.isEqual(cmd, "new"))
			{
				doNew(req, resp);
			}
			else if (StringUtil.isEqual(cmd, "edit"))
			{
				doEdit(req, resp);
			}
			else if (StringUtil.isEqual(cmd, "save"))
			{
				doSave(req, resp, true);
			}
			else if (StringUtil.isEqual(cmd, "delete"))
			{
				doDelete(req, resp);
			}
			else
			{
				log.debug("Tried accessing user with cmd: " + cmd);
				displayError(req, resp, "The command used to access this page is invalid");
			}
		}
	}
	
	private void doView(HttpServletRequest req, HttpServletResponse resp)
	{
		req.setAttribute("title", "Driver Home");
		displayPage(req, resp, JSP_PATH+"/view.jsp");
	}
	
	private void doLogin(HttpServletRequest req, HttpServletResponse resp)
	{
		String submitted = getParameterString(req, "submitted");
		if (!StringUtil.isNullOrEmpty(submitted))
		{
			String username = getParameterString(req, "username");
			boolean validLogin = false;
			Users user = serviceFactory.getInstance(ServiceType.USER, UsersService.class).fetchUserByUsername(username);
			if (user != null)
			{
				String password = getParameterString(req, "password");
				validLogin = BCrypt.checkpw(password, user.getPassword());
			}

			if (validLogin)
			{
				setSessionValue(req, USER_SESSION_KEY, user);
				displayPage(req, resp, JSP_PATH, true);
			}
			else
			{
				req.setAttribute("generalError", "Username/Password combination was invalid.");
				req.setAttribute("title", "Login");
				displayPage(req, resp, JSP_PATH+"/login.jsp");
			}
		}
		else
		{
			req.setAttribute("title", "Login");
			displayPage(req, resp, JSP_PATH+"/login.jsp");
		}
	}
	
	private void doLogout(HttpServletRequest req, HttpServletResponse resp)
	{
		setSessionValue(req, USER_SESSION_KEY, null);
		displayPage(req, resp, JSP_PATH, true);
	}
	
	private void doNew(HttpServletRequest req, HttpServletResponse resp)
	{
		
	}
	
	private void doEdit(HttpServletRequest req, HttpServletResponse resp)
	{
		
	}
	
	private void doSave(HttpServletRequest req, HttpServletResponse resp, boolean isAjax)
	{
		Users user = new Users();
		user.setUsername(getParameterString(req, "username"));
		String salt = BCrypt.gensalt(BCRYPT_SALT_ROUNDS);
		String hashedPassword = BCrypt.hashpw(getParameterString(req, "password"), salt);
		user.setPassword(hashedPassword);
		
		if (isAjax)
		{
			
		}
		else
		{
			displayPage(req, resp, JSP_PATH, true);
		}
	}
	
	private void doDelete(HttpServletRequest req, HttpServletResponse resp)
	{
		
	}
	
	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.USERS;
	}
}
