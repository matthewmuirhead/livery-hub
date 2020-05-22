package com.codemaven.manager.servlet;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.manager.db.ServiceFactory;
import com.codemaven.manager.enums.NavBarZone;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/teams")
@Slf4j
public class TeamsServlet extends ServletBase
{
	private static final String JSP_PATH = "teams";
	private ServiceFactory serviceFactory;
	
	@Override
	protected NavBarZone getNavBarZone()
	{
		return null;
	}
}
