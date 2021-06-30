package com.codemaven.liveries.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.liveries.manager.enums.NavBarZone;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/")
public class DashboardServlet extends ServletBase
{
	private static final String JSP_PATH = "hub/dashboard.jsp";

	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		req.setAttribute("isDashboard", true);
		displayPage(req, resp, JSP_PATH);
	}

	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.DASHBOARD;
	}
}
