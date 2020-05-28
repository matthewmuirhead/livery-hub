package com.codemaven.events.manager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.events.db.ServiceFactory;
import com.codemaven.events.db.ServiceType;
import com.codemaven.events.db.service.CarsService;
import com.codemaven.events.db.service.DriversService;
import com.codemaven.events.db.service.TeamsService;
import com.codemaven.events.manager.enums.NavBarZone;
import com.codemaven.events.model.AjaxSaveReplyJson;
import com.codemaven.events.model.CarsExtended;
import com.codemaven.events.manager.model.TeamDetails;
import com.codemaven.events.servlet.ServletBase;
import com.codemaven.events.util.StringUtil;
import com.codemaven.generated.tables.pojos.Drivers;
import com.codemaven.generated.tables.pojos.Teams;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/manager/teams")
@Slf4j
public class TeamsServlet extends ServletBase
{
	private static final String JSP_PATH = "teams";
	private ServiceFactory serviceFactory;
	
	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		try {
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
		if (StringUtil.isNullOrEmpty(cmd) || StringUtil.isEqual(cmd, "list")) {
			doList(req, resp);
		}
		else if (StringUtil.isEqual(cmd, "manager")) {
			doManager(req, resp);
		}
		else if (StringUtil.isEqual(cmd, "new")) {
			doNew(req, resp);
		}
		else if (StringUtil.isEqual(cmd, "edit")) {
			doEdit(req, resp);
		}
		else if (StringUtil.isEqual(cmd, "save")) {
			doSave(req, resp, false);
		}
		else if (StringUtil.isEqual(cmd, "ajaxSave")) {
			doSave(req, resp, true);
		}
		else if (StringUtil.isEqual(cmd, "ajaxDelete")) {
			doDelete(req, resp, true);
		}
	}
	
	private void doList(HttpServletRequest req, HttpServletResponse resp)
	{
		List<Teams> teams = serviceFactory.getInstance(ServiceType.TEAM, TeamsService.class).fetchAllTeams();
		List<TeamDetails> teamDetailsList = new ArrayList<>();
		for (Teams team : teams)
		{
			TeamDetails teamDetails = new TeamDetails(serviceFactory, team);
			teamDetailsList.add(teamDetails);
		}
		req.setAttribute("teamDetailsList", teamDetailsList);
		req.setAttribute("title", "Teams List");
		displayPage(req, resp, JSP_PATH+"/list.jsp");
	}
	
	private void doNew(HttpServletRequest req, HttpServletResponse resp)
	{
		int eventId = getParameterInt(req, "eventId");
		if (eventId > 0)
		{
			List<CarsExtended> cars = serviceFactory.getInstance(ServiceType.CAR, CarsService.class).fetchAllCarsSorted();
			req.setAttribute("cars", cars);
			List<Drivers> drivers = serviceFactory.getInstance(ServiceType.DRIVER, DriversService.class).fetchAllDrivers();
			req.setAttribute("drivers", drivers);
			req.setAttribute("title", "New Team");
			req.setAttribute("eventId", eventId);
			displayPage(req, resp, JSP_PATH+"/edit.jsp");
		}
		else
		{
			displayPage(req, resp, JSP_PATH, true);
		}
	}
	
	private void doEdit(HttpServletRequest req, HttpServletResponse resp)
	{
		int teamId = getParameterInt(req, "teamId");
		doEdit(req, resp, teamId);
	}
	
	private void doEdit(HttpServletRequest req, HttpServletResponse resp, int teamId)
	{
		Teams team = serviceFactory.getInstance(ServiceType.TEAM, TeamsService.class).fetchTeamById(teamId);
		req.setAttribute("team", team);
		List<CarsExtended> cars = serviceFactory.getInstance(ServiceType.CAR, CarsService.class).fetchAllCarsSorted();
		req.setAttribute("cars", cars);
		List<Drivers> drivers = serviceFactory.getInstance(ServiceType.DRIVER, DriversService.class).fetchAllDrivers();
		req.setAttribute("drivers", drivers);
		req.setAttribute("title", "Edit Team: "+team.getName());
		displayPage(req, resp, JSP_PATH+"/edit.jsp");
	}
	
	private void doSave(HttpServletRequest req, HttpServletResponse resp, boolean isAjax) throws IOException
	{
		AjaxSaveReplyJson replyJson = new AjaxSaveReplyJson();
		Teams team = populateTeamFromRequest(req);
		boolean saved = serviceFactory.getInstance(ServiceType.TEAM, TeamsService.class).saveTeam(team);
		if (isAjax)
		{
			if (saved)
			{
				// Success Ajax Response
				replyJson.addErrorMessage(AjaxSaveReplyJson.SUCCESS_KEY, "Event " + team.getName() + " saved successfully.");
				resp.setContentType("application/json");
				resp.getWriter().write(replyJson.toJsonString());
			}
			else
			{
				// Failed Ajax Response
				replyJson.addErrorMessage(AjaxSaveReplyJson.GENERAL_ERROR, "Could not save event " + team.getName() + ".");
				resp.setContentType("application/json");
				resp.getWriter().write(replyJson.toJsonString());
			}
		}
		else
		{
			if (saved)
			{
				displayPage(req, resp, JSP_PATH+"?cmd=manager&id="+team.getId(), true);
			}
			else
			{
				req.setAttribute(AjaxSaveReplyJson.GENERAL_ERROR, "Could not save event " + team.getName() + ".");
				int eventId = team.getEventId();
				if (eventId > 0)
				{
					doEdit(req, resp, eventId);
				}
				
			}
		}
	}
	
	private Teams populateTeamFromRequest(HttpServletRequest req)
	{
		Teams team = new Teams();
		team.setId(getParameterInt(req, "teamId"));
		team.setEventId(getParameterInt(req, "eventId"));
		team.setName(getParameterString(req, "name"));
		team.setDriver_1(getParameterInt(req, "driver1"));
		team.setDriver_2(getParameterInt(req, "driver2"));
		team.setDriver_3(getParameterInt(req, "driver3"));
		team.setDriver_4(getParameterInt(req, "driver4"));
		team.setCarId(getParameterInt(req, "carId"));
		team.setNumber(getParameterInt(req, "number"));
		team.setCategory(getParameterString(req, "category"));
		team.setStatus(getParameterString(req, "status"));
		return team;
	}
	
	private void doDelete(HttpServletRequest req, HttpServletResponse resp, boolean isAjax) throws IOException
	{
		TeamsService service = serviceFactory.getInstance(ServiceType.TEAM, TeamsService.class);
		int teamId = getParameterInt(req, "teamId");
		// Make sure to delete dependencies
		service.deleteTeamFuelByTeamId(teamId);
		service.deleteTeamTiresByTeamId(teamId);
		// Delete actual team
		boolean success = service.deleteTeamById(teamId);
		if (isAjax)
		{
			if (success)
			{
				String replyJson = "{\"successMessage\":\"Team with ID "+teamId+" successfully deleted\",\"removedId\":\""+teamId+"\"}";
				resp.setContentType("application/json");
				resp.getWriter().write(replyJson);
			}
			else
			{
				String replyJson = "{\"errorMessage\":\"Could not delete team with ID "+teamId+"\"}";
				resp.setContentType("application/json");
				resp.getWriter().write(replyJson);
			}
		}
	}
	
	private void doManager(HttpServletRequest req, HttpServletResponse resp)
	{
		
	}
	
	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.TEAMS;
	}
}
