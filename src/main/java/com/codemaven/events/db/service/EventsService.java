package com.codemaven.events.db.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.codemaven.events.db.Service;
import com.codemaven.events.db.ServiceType;
import com.codemaven.events.db.dao.EventsDao;
import com.codemaven.generated.tables.pojos.ExternalEvents;
import com.codemaven.generated.tables.pojos.Sessions;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class EventsService implements Service
{
	private EventsDao dao;
	
	public List<ExternalEvents> fetchEventsAfterDate(final LocalDateTime after)
	{
		List<ExternalEvents> events = new ArrayList<>();
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

	public List<ExternalEvents> fetchEventsBetweenDates(final LocalDateTime before, final LocalDateTime after)
	{
		List<ExternalEvents> events = new ArrayList<>();
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
	
	public ExternalEvents fetchEventById(final int eventId)
	{
		ExternalEvents events = null;
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
	
	public Sessions fetchSessionById(final int sessionId)
	{
		Sessions session = null;
		if (sessionId > 0)
		{
			session = dao.fetchSessionById(sessionId);
		}
		else
		{
			log.debug("Tried fetching sessions with event id " + sessionId);
		}
		return session;
	}
	
	public boolean saveSession(final Sessions session)
	{
		boolean saved = false;
		if (session != null)
		{
			saved = dao.saveSession(session);
		}
		else
		{
			log.debug("Tried saving null session");
		}
		return saved;
	}
	
	public boolean saveEvent(final ExternalEvents event)
	{
		boolean saved = false;
		if (event != null)
		{
			saved = dao.saveEvent(event);
		}
		else
		{
			log.debug("Tried saving null event");
		}
		return saved;
	}
	
	public boolean deleteSessionById(final int sessionId)
	{
		boolean deleted = true;
		if (sessionId > 0)
		{
			deleted = dao.deleteSessionById(sessionId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried deleting team with id " + sessionId);
		}
		return deleted;
	}
	
	@Override
	public ServiceType getType()
	{
		return ServiceType.EVENT;
	}
}
