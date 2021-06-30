package com.codemaven.liveries.manager.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.codemaven.generated.tables.pojos.ExternalEvents;
import com.codemaven.generated.tables.pojos.Hosts;
import com.codemaven.generated.tables.pojos.Locations;
import com.codemaven.generated.tables.pojos.Sessions;
import com.codemaven.generated.tables.pojos.Teams;
import com.codemaven.generated.tables.pojos.Tracks;
import com.codemaven.liveries.db.ServiceFactory;
import com.codemaven.liveries.db.ServiceType;
import com.codemaven.liveries.db.service.EventsService;
import com.codemaven.liveries.db.service.HostsService;
import com.codemaven.liveries.db.service.TeamsService;
import com.codemaven.liveries.db.service.TracksService;

import lombok.Setter;

@Setter
public class ExternalEventDetails
{
	private ServiceFactory serviceFactory;
	private int eventId;
	private ExternalEvents event;
	private List<TeamDetails> teams;
	private List<Sessions> sessions;
	private Hosts host;
	private Tracks track;
	private Locations location;
	private LocalDateTime calendarStart;
	
	public ExternalEventDetails(ServiceFactory serviceFactory, int eventId)
	{
		this.serviceFactory = serviceFactory;
		this.eventId = eventId;
	}
	
	public ExternalEventDetails(ServiceFactory serviceFactory, ExternalEvents event)
	{
		this.serviceFactory = serviceFactory;
		this.event = event;
		this.eventId = event.getId();
	}
	
	public void populate()
	{
		getEvent();
		getTeams();
		getSessions();
		getHost();
		getTrack();
	}
	
	public int getEventId()
	{
		return eventId;
	}
	
	public ExternalEvents getEvent()
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
			TeamsService teamsService = serviceFactory.getInstance(ServiceType.TEAM, TeamsService.class);
			List<Teams> fetchedTeams = teamsService.fetchEventsTeams(eventId);
			for (Teams team : fetchedTeams)
			{
				TeamDetails details = new TeamDetails(serviceFactory, team);
				teams.add(details);
			}
		}
		return teams;
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
			host = serviceFactory.getInstance(ServiceType.HOST, HostsService.class).fetchHostById(getEvent().getHostId());
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
	
	public Locations getLocation()
	{
		if (location == null)
		{
			location = serviceFactory.getInstance(ServiceType.TRACK, TracksService.class).fetchLocationById(getTrack().getLocationId());
		}
		return location;
	}
	
	public LocalDateTime getCalendarStart()
	{
		return calendarStart;
	}
	
	public String getCalendarDisplay()
	{
		LocalDateTime eventDate = getEvent().getEventDate();
		int slot = (int) ChronoUnit.DAYS.between(calendarStart, eventDate)+1;
		int row = (slot/7)+2;
		int column = slot%7;
		String display = "style='grid-row:"+row+";grid-column:"+column+";'";
		return display;
	}
}
