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
		if (eventId > 0)
		{
			teams = dao.fetchSeriesTeams(seriesId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching teams with series id " + eventId);
		}
		return teams;
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
