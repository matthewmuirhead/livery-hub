package com.codemaven.manager.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

import lombok.Setter;

@Setter
public class EventDetails
{
	private ServiceFactory serviceFactory;
	private int eventId;
	private Events event;
	private List<Teams> teams;
	private List<Sessions> sessions;
	private Hosts host;
	private Tracks track;
	private LocalDateTime calendarStart;
	
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
		getTeams();
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
	
	public List<Teams> getTeams()
	{
		if (teams == null || teams.size() == 0)
		{
			teams = serviceFactory.getInstance(ServiceType.TEAM, TeamsService.class).fetchEventsTeams(eventId);
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
		boolean saved = true;
		EventsService eventService = serviceFactory.getInstance(ServiceType.EVENT, EventsService.class);
		saved = saved && eventService.saveEvent(getEvent());
		return saved;
	}
	
	public LocalDateTime getCalendarStart()
	{
		return calendarStart;
	}
	
	public String getCalendarDisplay()
	{
		LocalDateTime eventDate = getEvent().getEventDate();
		int slot = (int) ChronoUnit.DAYS.between(calendarStart, eventDate)+1;
		int row = slot/5;
		int column = slot%7;
		String display = "style=\"grid-row:"+row+";grid-column:"+column+";\"";
		return display;
	}
}
