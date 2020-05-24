package com.codemaven.manager.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.manager.db.ServiceFactory;
import com.codemaven.manager.db.ServiceType;
import com.codemaven.manager.db.service.TeamsService;
import com.codemaven.manager.enums.NavBarZone;
import com.codemaven.manager.util.StringUtil;

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
		else if (StringUtil.isEqual(cmd, "ajaxDelete")) {
			doDelete(req, resp, true);
		}
	}
	
	private void doList(HttpServletRequest req, HttpServletResponse resp)
	{
		
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
	
	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.TEAMS;
	}
}
