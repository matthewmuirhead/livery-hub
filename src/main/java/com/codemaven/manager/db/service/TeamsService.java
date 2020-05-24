package com.codemaven.manager.db.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.codemaven.generated.tables.pojos.TeamFuel;
import com.codemaven.generated.tables.pojos.TeamTires;
import com.codemaven.generated.tables.pojos.Teams;
import com.codemaven.manager.db.Service;
import com.codemaven.manager.db.ServiceType;
import com.codemaven.manager.db.dao.TeamsDao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class TeamsService implements Service
{
	private TeamsDao dao;
	
	public List<Teams> fetchEventsTeams(final int eventId)
	{
		List<Teams> teams = new ArrayList<>();
		if (eventId > 0)
		{
			teams = dao.fetchEventsTeams(eventId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching teams with event id " + eventId);
		}
		return teams;
	}
	
	public Teams fetchTeamById(final int teamId)
	{
		Teams team = null;
		if (teamId > 0)
		{
			team = dao.fetchTeamById(teamId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching team with id " + teamId);
		}
		return team;
	}
	
	public List<Teams> fetchAllTeams()
	{
		return dao.fetchAllTeams();
	}
	
	public List<TeamTires> fetchTeamTiresByTeamId(final int teamId)
	{
		List<TeamTires> teamTires = new ArrayList<>();
		if (teamId > 0)
		{
			teamTires = dao.fetchTeamTiresByTeamId(teamId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching team with id " + teamId);
		}
		return teamTires;
	}
	
	public List<TeamFuel> fetchTeamFuelByTeamId(final int teamId)
	{
		List<TeamFuel> teamFuels = new ArrayList<>();
		if (teamId > 0)
		{
			teamFuels = dao.fetchTeamFuelByTeamId(teamId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching team with id " + teamId);
		}
		return teamFuels;
	}
	
	public boolean deleteTeamTiresByTeamId(final int teamId)
	{
		boolean deleted = true;
		if (teamId > 0)
		{
			deleted = dao.deleteTeamTiresByTeamId(teamId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried deleting team tires with team id " + teamId);
		}
		return deleted;
	}
	
	public boolean deleteTeamFuelByTeamId(final int teamId)
	{
		boolean deleted = true;
		if (teamId > 0)
		{
			deleted = dao.deleteTeamFuelByTeamId(teamId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried deleting team fuel with team id " + teamId);
		}
		return deleted;
	}
	
	public boolean deleteTeamById(final int teamId)
	{
		boolean deleted = true;
		if (teamId > 0)
		{
			deleted = dao.deleteTeamById(teamId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried deleting team with id " + teamId);
		}
		return deleted;
	}
	
	@Override
	public ServiceType getType()
	{
		return ServiceType.TEAM;
	}
}
