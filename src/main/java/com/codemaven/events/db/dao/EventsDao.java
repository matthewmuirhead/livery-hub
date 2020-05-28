package com.codemaven.events.db.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.codemaven.generated.Tables;
import com.codemaven.generated.tables.pojos.Events;
import com.codemaven.generated.tables.pojos.Sessions;
import com.codemaven.generated.tables.records.EventsRecord;
import com.codemaven.generated.tables.records.SessionsRecord;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class EventsDao
{
	private final DSLContext dsl;

	public List<Events> fetchEventsAfterDate(final LocalDateTime after)
	{
		return dsl.selectFrom(Tables.EVENTS)
				.where(Tables.EVENTS.EVENT_DATE.ge(after))
				.fetchInto(Events.class);
	}

	public List<Events> fetchEventsBetweenDates(final LocalDateTime before, final LocalDateTime after)
	{
		return dsl.selectFrom(Tables.EVENTS)
				.where(Tables.EVENTS.EVENT_DATE.ge(before))
				.and(Tables.EVENTS.EVENT_DATE.lt(after))
				.fetchInto(Events.class);
	}

	public Events fetchEventById(final int eventId)
	{
		return dsl.selectFrom(Tables.EVENTS)
				.where(Tables.EVENTS.ID.eq(eventId))
				.fetchOneInto(Events.class);
	}
	
	public List<Sessions> fetchSessionsByEventId(final int eventId)
	{
		return dsl.selectFrom(Tables.SESSIONS)
				.where(Tables.SESSIONS.EVENT_ID.eq(eventId))
				.fetchInto(Sessions.class);
	}
	
	public Sessions fetchSessionById(final int sessionId)
	{
		return dsl.selectFrom(Tables.SESSIONS)
				.where(Tables.SESSIONS.ID.eq(sessionId))
				.fetchOneInto(Sessions.class);
	}
	
	public boolean saveSession(final Sessions session)
	{
		SessionsRecord record = dsl.newRecord(Tables.SESSIONS, session);
		return dsl.insertInto(Tables.SESSIONS).set(record).onDuplicateKeyUpdate().set(record).execute() > 0;
	}
	
	public boolean saveEvent(final Events event)
	{
		EventsRecord record = dsl.newRecord(Tables.EVENTS, event);
		return dsl.insertInto(Tables.EVENTS).set(record).onDuplicateKeyUpdate().set(record).execute() > 0;
	}
	
	public boolean deleteSessionById(final int sessionId)
	{
		return dsl.deleteFrom(Tables.SESSIONS)
				.where(Tables.SESSIONS.ID.eq(sessionId)).execute() > 0;
	}
}
