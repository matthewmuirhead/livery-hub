package com.codemaven.manager.model;

import java.util.ArrayList;
import java.util.List;

import com.codemaven.generated.tables.pojos.Events;
import com.codemaven.generated.tables.pojos.Hosts;
import com.codemaven.generated.tables.pojos.Sessions;
import com.codemaven.generated.tables.pojos.Teams;
import com.codemaven.generated.tables.pojos.Tracks;
import com.codemaven.manager.db.ServiceFactory;
import com.codemaven.manager.db.ServiceType;
import com.codemaven.manager.db.service.EventsService;
import com.codemaven.manager.db.service.TeamsService;
import com.codemaven.manager.db.service.TracksService;

public class EventDetails
{
	private ServiceFactory serviceFactory;
	private int eventId;
	private Events event;
	private List<TeamDetails> teams;
	private List<Sessions> sessions;
	private Hosts host;
	private Tracks track;
	
	public EventDetails(ServiceFactory serviceFactory, int eventId)
	{
		this.serviceFactory = serviceFactory;
		this.eventId = eventId;
	}
	
	public EventDetails(ServiceFactory serviceFactory, Events event)
	{
		this.serviceFactory = serviceFactory;
		this.event = event;
		this.eventId = event.getId();
	}
	
	public void populate()
	{
		getEvent();
		populateTeams();
		getSessions();
		getHost();
		getTrack();
	}
	
	public int getEventId()
	{
		return eventId;
	}
	
	public Events getEvent()
	{
		if (event == null)
		{
			event = serviceFactory.getInstance(ServiceType.EVENT, EventsService.class).fetchEventById(eventId);
		}
		return event;
	}
	
	public List<TeamDetails> getTeams()
	{
		if (teams == null || teams.size() == 0)
		{
			teams = new ArrayList<>();
			TeamsService teamService = serviceFactory.getInstance(ServiceType.TEAM, TeamsService.class);
			List<Teams> fetchedTeams = teamService.fetchEventsTeams(eventId);
			for (Teams team : fetchedTeams)
			{
				TeamDetails details = new TeamDetails(teamService, team);
				teams.add(details);
			}
		}
		return teams;
	}
	
	public void populateTeams()
	{
		for (TeamDetails team : getTeams())
		{
			team.populate();
		}
	}
	
	public List<Sessions> getSessions()
	{
		if (sessions == null || sessions.size() == 0)
		{
			sessions = serviceFactory.getInstance(ServiceType.EVENT, EventsService.class).fetchSessionsByEventId(eventId);
		}
		return sessions;
	}
	
	public Hosts getHost()
	{
		if (host == null)
		{
			host = new Hosts();
		}
		return host;
	}
	
	public Tracks getTrack()
	{
		if (track == null)
		{
			track = serviceFactory.getInstance(ServiceType.TRACK, TracksService.class).fetchTrackById(getEvent().getTrackId());
		}
		return track;
	}
	
	public boolean save()
	{
		boolean saved = false;
		EventsService eventService = serviceFactory.getInstance(ServiceType.EVENT, EventsService.class);
		eventService.saveEvent(getEvent());
		return saved;
	}
}
