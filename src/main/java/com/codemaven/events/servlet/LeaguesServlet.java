package com.codemaven.events.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.events.db.ServiceFactory;
import com.codemaven.events.manager.enums.NavBarZone;
import com.codemaven.events.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/leagues")
@Slf4j
public class LeaguesServlet extends ServletBase
{
	private static final String JSP_PATH = "events";
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
		if (StringUtil.isNullOrEmpty(cmd))
		{
			
		}
	}
	
	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.LEAGUES;
	}
}