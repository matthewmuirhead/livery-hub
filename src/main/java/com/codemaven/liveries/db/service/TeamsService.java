package com.codemaven.liveries.db.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.codemaven.generated.tables.pojos.Teams;
import com.codemaven.liveries.db.Service;
import com.codemaven.liveries.db.ServiceType;
import com.codemaven.liveries.db.dao.TeamsDao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class TeamsService implements Service
{
	private TeamsDao dao;

	public List<Teams> fetchSeriesTeams(final int seriesId)
	{
		List<Teams> teams = new ArrayList<>();
		if (seriesId > 0)
		{
			teams = dao.fetchSeriesTeams(seriesId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching teams with series id " + seriesId);
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
			log.debug("Tried fetching teams with id " + teamId);
		}
		return team;
	}

	public boolean saveTeam(final Teams team)
	{
		boolean saved = false;
		if (team != null)
		{
			saved = dao.saveTeam(team);
		}
		else
		{
			log.debug("Tried saving null team");
		}
		return saved;
	}

	public boolean deleteTeamByIdAndUser(final int teamId, final int userId)
	{
		boolean deleted = true;
		if (teamId > 0 && userId > 0)
		{
			deleted = dao.deleteTeamByIdAndUser(teamId, userId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried deleting team with id " + teamId);
		}
		return deleted;
	}

	public List<Teams> fetchTeamsByUserId(final int userId)
	{
		List<Teams> teams = new ArrayList<>();
		if (userId > 0)
		{
			teams = dao.fetchTeamsByUserId(userId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching teams with user id " + userId);
		}
		return teams;
	}

	@Override
	public ServiceType getType()
	{
		return ServiceType.TEAM;
	}
}
