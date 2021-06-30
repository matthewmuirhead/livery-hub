package com.codemaven.liveries.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.generated.tables.pojos.Series;
import com.codemaven.liveries.db.ServiceFactory;
import com.codemaven.liveries.db.ServiceType;
import com.codemaven.liveries.db.service.SeriesService;
import com.codemaven.liveries.manager.enums.NavBarZone;
import com.codemaven.liveries.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/")
@Slf4j
public class DashboardServlet extends ServletBase
{
	private static final String JSP_PATH = "manager/dashboard.jsp";
	private ServiceFactory serviceFactory;

	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		try
		{
			req.setAttribute("isDashboard", true);
			displayPage(req, resp, JSP_PATH);
		}
		catch (Exception e)
		{
			log.error("Error processing cmd: " + e.getMessage(), e);
			displayError(req, resp, "Uh-Oh, something went wrong!");
		}
	}

	private void showSeries(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		
	}

	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.DASHBOARD;
	}
}
