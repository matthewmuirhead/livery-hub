package com.codemaven.liveries.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.liveries.db.ServiceFactory;
import com.codemaven.liveries.db.ServiceType;
import com.codemaven.liveries.db.service.TeamsService;
import com.codemaven.liveries.manager.enums.NavBarZone;
import com.codemaven.liveries.model.AjaxSaveReplyJson;
import com.codemaven.liveries.model.ExtendedUser;
import com.codemaven.liveries.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/series/team")
@Slf4j
public class TeamsServlet extends ServletBase
{
	private static final String JSP_PATH = "series/team/";
	private ServiceFactory serviceFactory;

	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		try
		{
			ExtendedUser user = getLoggedInUser(req);
			int teamId = getParameterInt(req, "teamId");
			if (!user.canAccessTeam(teamId))
			{
				
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
			if (StringUtil.isEqual("new", cmd))
			{
				doNewTeam(req, resp);
			}
			else if (StringUtil.isEqual("edit", cmd))
			{
				doEditTeam(req, resp);
			}
			else if (StringUtil.isEqual("save", cmd))
			{
				
			}
			else if (StringUtil.isEqual("ajaxSave", cmd))
			{
				
			}
			else if (StringUtil.isEqual("delete", cmd))
			{
				doDeleteTeam(req, resp);
			}
		}
	}
	
	private void doNewTeam(HttpServletRequest req, HttpServletResponse resp)
	{
		Teams team = new Teams();
		displayPage(req, resp, JSP_PATH + "edit.jsp");
	}
	
	private void doEditTeam(HttpServletRequest req, HttpServletResponse resp)
	{
		int teamId = getParameterInt(req, "teamId");
		Teams team = serviceFactory.getInstance(ServiceType.TEAM, TeamsService.class).fetchTeamById(teamId);
		displayPage(req, resp, JSP_PATH + "edit.jsp");
	}

	private void doDeleteTeam(HttpServletRequest req, HttpServletResponse resp)
	{
		int teamId = getParameterInt(req, "teamId");
		ExtendedUser user = getLoggedInUser(req);
		
		boolean success = serviceFactory.getInstance(ServiceType.TEAM, TeamsService.class).deleteTeamByIdAndUser(teamId, user.getId());
		req.setAttribute("success", success);
		if (success)
		{
			req.setAttribute("successMessage", "The team was successfully removed.");
		}
		else
		{
			req.setAttribute("errorMessage", "There was an error deleting the team, please try again later.");
		}
		int seriesId = getParameterInt(req, "seriesId");
		displayPage(req, resp, "series?id="+seriesId, true);
	}

	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.SERIES;
	}
}