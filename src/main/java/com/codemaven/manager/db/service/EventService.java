package com.codemaven.manager.db.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.codemaven.generated.tables.pojos.Events;
import com.codemaven.generated.tables.pojos.Sessions;
import com.codemaven.manager.db.Service;
import com.codemaven.manager.db.ServiceType;
import com.codemaven.manager.db.dao.EventDao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class EventService implements Service
{
	private EventDao dao;
	
	public List<Events> fetchEventsAfterDate(final LocalDateTime after)
	{
		List<Events> events = new ArrayList<>();
		if (after != null)
		{
			events = dao.fetchEventsAfterDate(after);
		}
		else
		{
			log.debug("Tried fetching events after null date");
		}
		return events;
	}

	public List<Events> fetchEventsBetweenDates(final LocalDateTime before, final LocalDateTime after)
	{
		List<Events> events = new ArrayList<>();
		if (before != null && after != null)
		{
			events = dao.fetchEventsBetweenDates(before, after);
		}
		else
		{
			log.debug("Tried fetching events between null dates");
		}
		return events;
	}
	
	public Events fetchEventById(final int eventId)
	{
		Events events = null;
		if (eventId > 0)
		{
			events = dao.fetchEventById(eventId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching event with id " + eventId);
		}
		return events;
	}
	
	public List<Sessions> fetchSessionsByEventId(final int eventId)
	{
		List<Sessions> sessions = new ArrayList<>();
		if (eventId > 0)
		{
			sessions = dao.fetchSessionsByEventId(eventId);
		}
		else
		{
			log.debug("Tried fetching sessions with event id " + eventId);
		}
		return sessions;
	}
	
	@Override
	public ServiceType getType()
	{
		return ServiceType.EVENT;
	}
}
