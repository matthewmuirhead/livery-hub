package com.codemaven.events.admin.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.events.db.ServiceFactory;
import com.codemaven.events.manager.enums.NavBarZone;
import com.codemaven.events.servlet.ServletBase;
import com.codemaven.events.util.StringUtil;
import com.codemaven.generated.tables.pojos.Users;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/admin/users")
@Slf4j
public class UsersServlet extends ServletBase
{
	private static final String JSP_PATH = "admin/users";
	private ServiceFactory serviceFactory;
	
	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		try
		{
			Users user = getLoggedInUser(req);
			if (user == null || !user.getAdmin())
			{
				// Redirect to homepage
				displayPage(req, resp, "", true);
			}
			else
			{
				doCmd(req, resp);
			}
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
		if (StringUtil.isNullOrEmpty(cmd))
		{
			
		}
	}
	
	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.ADMIN_USERS;
	}
}
