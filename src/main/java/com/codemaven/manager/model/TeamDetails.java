package com.codemaven.manager.model;

import java.util.ArrayList;
import java.util.List;

import com.codemaven.generated.tables.pojos.Drivers;
import com.codemaven.generated.tables.pojos.TeamFuel;
import com.codemaven.generated.tables.pojos.TeamTires;
import com.codemaven.generated.tables.pojos.Teams;
import com.codemaven.manager.db.ServiceFactory;
import com.codemaven.manager.db.ServiceType;
import com.codemaven.manager.db.service.CarsService;
import com.codemaven.manager.db.service.DriversService;
import com.codemaven.manager.db.service.TeamsService;

import lombok.Setter;

@Setter
public class TeamDetails
{
	private TeamsService teamsService;
	private DriversService driversService;
	private CarsService carsService;
	private int teamId;
	private Teams team;
	private List<Drivers> drivers;
	private List<TeamFuel> fuel;
	private List<TeamTires> tires;
	private CarsExtended car;
	
	public TeamDetails(ServiceFactory serviceFactory, int teamId)
	{
		this.teamsService = serviceFactory.getInstance(ServiceType.TEAM, TeamsService.class);
		this.driversService = serviceFactory.getInstance(ServiceType.DRIVER, DriversService.class);
		this.carsService = serviceFactory.getInstance(ServiceType.CAR, CarsService.class);
		this.teamId = teamId;
	}
	
	public TeamDetails(ServiceFactory serviceFactory, Teams team)
	{
		this.teamsService = serviceFactory.getInstance(ServiceType.TEAM, TeamsService.class);
		this.driversService = serviceFactory.getInstance(ServiceType.DRIVER, DriversService.class);
		this.carsService = serviceFactory.getInstance(ServiceType.CAR, CarsService.class);
		this.team = team;
		this.teamId = team.getId();
	}
	
	public void populate()
	{
		getTeam();
		getDrivers();
		getFuel();
		getTires();
		getCar();
	}
	
	public int getTeamId()
	{
		return teamId;
	}
	
	public Teams getTeam()
	{
		if (team == null)
		{
			team = teamsService.fetchTeamById(teamId);
		}
		return team;
	}
	
	public List<TeamFuel> getFuel()
	{
		if (fuel == null)
		{
			fuel = teamsService.fetchTeamFuelByTeamId(teamId);
		}
		return fuel;
	}
	
	public List<TeamTires> getTires()
	{
		if (tires == null)
		{
			tires = teamsService.fetchTeamTiresByTeamId(teamId);
		}
		return tires;
	}
	
	public CarsExtended getCar()
	{
		if (car == null)
		{
			car = carsService.fetchCarById(getTeam().getCarId());
		}
		return car;
	}
	
	public List<Drivers> getDrivers()
	{
		if (drivers == null || drivers.size() == 0)
		{
			drivers = new ArrayList<>();
			Teams team = getTeam();
			addDriver(drivers, driversService.fetchDriverById(team.getDriver_1()));
			addDriver(drivers, driversService.fetchDriverById(team.getDriver_2()));
			addDriver(drivers, driversService.fetchDriverById(team.getDriver_3()));
			addDriver(drivers, driversService.fetchDriverById(team.getDriver_4()));
		}
		return drivers;
	}
	
	private void addDriver(List<Drivers> drivers, Drivers driver)
	{
		if (driver != null) 
		{
			drivers.add(driver);
		}
	}
	
	public String getStatusStyle()
	{
		switch (getTeam().getStatus())
		{
			case "Registered":
				return "border-success";
			case "Provisional":
				return "border-warning";
			default:
				return "border-danger";
		}
	}
}
