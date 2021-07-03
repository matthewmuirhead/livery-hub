package com.codemaven.liveries.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.liveries.enums.NavBarZone;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/logout")
@Slf4j
public class LogoutServlet extends ServletBase
{
	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		try
		{
			setSessionValue(req, USER_SESSION_KEY, null);
			displayPage(req, resp, "/", true);
		}
		catch (Exception e)
		{
			log.error("Error processing cmd: " + e.getMessage(), e);
			displayError(req, resp, "Uh-Oh, something went wrong!");
		}
	}

	@Override
	protected NavBarZone getNavBarZone()
	{
		return null;
	}
}
