package com.codemaven.manager.model;

import java.util.List;

import com.codemaven.generated.tables.pojos.TeamFuel;
import com.codemaven.generated.tables.pojos.TeamTires;
import com.codemaven.generated.tables.pojos.Teams;
import com.codemaven.manager.db.service.TeamsService;

import lombok.Setter;

@Setter
public class TeamDetails
{
	private TeamsService teamService;
	private int teamId;
	private Teams team;
	private List<TeamFuel> fuel;
	private List<TeamTires> tires;
	
	public TeamDetails(TeamsService teamService, int teamId)
	{
		this.teamService = teamService;
		this.teamId = teamId;
	}
	
	public TeamDetails(TeamsService teamService, Teams team)
	{
		this.teamService = teamService;
		this.team = team;
		this.teamId = team.getId();
	}
	
	public void populate()
	{
		getTeam();
		getFuel();
		getTires();
	}
	
	public int getTeamId()
	{
		return teamId;
	}
	
	public Teams getTeam()
	{
		if (team == null)
		{
			team = teamService.fetchTeamById(teamId);
		}
		return team;
	}
	
	public List<TeamFuel> getFuel()
	{
		if (fuel == null)
		{
			fuel = teamService.fetchTeamFuelByTeamId(teamId);
		}
		return fuel;
	}
	
	public List<TeamTires> getTires()
	{
		if (tires == null)
		{
			tires = teamService.fetchTeamTiresByTeamId(teamId);
		}
		return tires;
	}
}
