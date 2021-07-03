package com.codemaven.liveries.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.generated.tables.pojos.Series;
import com.codemaven.generated.tables.pojos.Teams;
import com.codemaven.liveries.db.ServiceFactory;
import com.codemaven.liveries.db.ServiceType;
import com.codemaven.liveries.db.service.SeriesService;
import com.codemaven.liveries.db.service.TeamsService;
import com.codemaven.liveries.manager.enums.NavBarZone;
import com.codemaven.liveries.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/series")
@Slf4j
public class SeriesServlet extends ServletBase
{
	private static final String JSP_PATH = "hub/series/";
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
		if (StringUtil.isEqual(cmd, "view"))
		{
			doView(req, resp);
		}
		else
		{
			displaySeries(req, resp);
		}
	}

	private void displaySeries(HttpServletRequest req, HttpServletResponse resp)
	{
		List<Series> series = serviceFactory.getInstance(ServiceType.SERIES, SeriesService.class).fetchAllSeries();
		req.setAttribute("seriesList", series);
		String view = getParameterString(req, "view");
		if (StringUtil.isEqual(view, "list"))
		{
			displayPage(req, resp, JSP_PATH + "list.jsp");
		}
		else
		{
			displayPage(req, resp, JSP_PATH + "grid.jsp");
		}
	}

	private void doView(HttpServletRequest req, HttpServletResponse resp)
	{
		int seriesId = getParameterInt(req, "id");
		Series series = serviceFactory.getInstance(ServiceType.SERIES, SeriesService.class).fetchSeriesById(seriesId);
		req.setAttribute("selectedSeries", series);
		List<Teams> teams = serviceFactory.getInstance(ServiceType.TEAM, TeamsService.class).fetchSeriesTeams(seriesId);
		req.setAttribute("teams", teams);
		displayPage(req, resp, JSP_PATH + "view.jsp");
	}

	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.SERIES;
	}
}