package com.codemaven.liveries.model;

import java.util.ArrayList;
import java.util.List;

import com.codemaven.generated.tables.pojos.Series;
import com.codemaven.generated.tables.pojos.Teams;
import com.codemaven.generated.tables.pojos.Users;
import com.codemaven.liveries.db.ServiceFactory;
import com.codemaven.liveries.db.ServiceType;
import com.codemaven.liveries.db.service.SeriesService;
import com.codemaven.liveries.db.service.TeamsService;

import lombok.Setter;

@Setter
public class ExtendedUser extends Users
{
	private static final long serialVersionUID = 1L;
	
	private List<Series> series = new ArrayList<>();
	private List<Teams> teams = new ArrayList<>();
	private ServiceFactory serviceFactory;

	public List<Series> getSeries()
	{
		if (series.isEmpty())
		{
			series = serviceFactory.getInstance(ServiceType.SERIES, SeriesService.class)
					.fetchAllSeries();
		}
		return series;
	}

	public List<Teams> getTeams()
	{
		if (teams.isEmpty())
		{
			teams = serviceFactory.getInstance(ServiceType.TEAM, TeamsService.class)
					.fetchTeamsByUserId(getId());
		}
		return teams;
	}

	public boolean canAccessSeries(int seriesId)
	{
		return getSeries().stream()
				.anyMatch(series -> series.getId()
						.intValue() == seriesId);
	}
	
	public boolean canAccessTeam(int teamId)
	{
		return getTeams().stream()
				.anyMatch(team -> team.getId()
						.intValue() == teamId);
	}
}
