package com.codemaven.liveries.admin.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.generated.tables.pojos.Series;
import com.codemaven.generated.tables.pojos.Users;
import com.codemaven.liveries.db.ServiceFactory;
import com.codemaven.liveries.db.ServiceType;
import com.codemaven.liveries.db.service.SeriesService;
import com.codemaven.liveries.enums.NavBarZone;
import com.codemaven.liveries.servlet.ServletBase;
import com.codemaven.liveries.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/admin/series")
@Slf4j
public class SeriesManager extends ServletBase
{
	private static final String JSP_PATH = "series/";
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
				displayPage(req, resp, "/", true);
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
		int seriesId = getParameterInt(req, "id");
		if (StringUtil.isEqual(cmd, "delete"))
		{
			doDelete(req, resp, seriesId);
		}
		if (StringUtil.isEqual(cmd, "new"))
		{
			doNew(req, resp);
		}
		if (StringUtil.isEqual(cmd, "save"))
		{
			
		}
		else if (seriesId > 0)
		{
			doEdit(req, resp, seriesId);
		}
		else
		{
			doList(req, resp);
		}
	}

	private void doDelete(HttpServletRequest req, HttpServletResponse resp, int seriesId)
	{
		
	}
	
	private void doNew(HttpServletRequest req, HttpServletResponse resp)
	{
		Series series = new Series();
		req.setAttribute("selectedSeries", series);
		displayPage(req, resp, JSP_PATH + "edit.jsp");
	}
	
	private void doEdit(HttpServletRequest req, HttpServletResponse resp, int seriesId)
	{
		Series series = serviceFactory.getInstance(ServiceType.SERIES, SeriesService.class).fetchSeriesById(seriesId);
		req.setAttribute("selectedSeries", series);
		displayPage(req, resp, JSP_PATH + "edit.jsp");
	}
	
	private void doList(HttpServletRequest req, HttpServletResponse resp)
	{
		List<Series> activeSeries = serviceFactory.getInstance(ServiceType.SERIES, SeriesService.class).fetchActiveSeries();
		req.setAttribute("activeSeries", activeSeries);
		List<Series> upcomingSeries = serviceFactory.getInstance(ServiceType.SERIES, SeriesService.class).fetchUpcomingSeries();
		req.setAttribute("upcomingSeries", upcomingSeries);
		List<Series> finishedSeries = serviceFactory.getInstance(ServiceType.SERIES, SeriesService.class).fetchFinishedSeries();
		req.setAttribute("finishedSeries", finishedSeries);
		displayPage(req, resp, JSP_PATH + "list.jsp");
	}
	
	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.ADMIN_SERIES;
	}
}
