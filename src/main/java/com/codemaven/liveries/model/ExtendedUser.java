package com.codemaven.liveries.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus.Series;

import com.codemaven.liveries.db.ServiceFactory;
import com.codemaven.liveries.db.ServiceType;
import com.codemaven.liveries.db.service.SeriesService;
import com.codemaven.liveries.db.service.TeamsService;

import lombok.Setter;

@Setter
public class ExtendedUser extends Users
{
	private List<Series> series = new ArrayList<>();
	private List<Teams> teams = new ArrayList<>();
	private ServiceFactory serviceFactory;

	public List<Series> getSeries()
	{
		if (series.isEmpty())
		{
			series = serviceFactory.getInstance(ServiceType.SERIES, SeriesService.class);
		}
		return series;
	}

	public List<Series> getTeams()
	{
		if (teams.isEmpty())
		{
			teams = serviceFactory.getInstance(ServiceType.TEAM, TeamsService.class);
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
