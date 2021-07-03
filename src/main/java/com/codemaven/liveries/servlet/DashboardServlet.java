package com.codemaven.liveries.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.liveries.enums.NavBarZone;

import lombok.AllArgsConstructor;

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
