package com.codemaven.events.db.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.codemaven.generated.Tables;
import com.codemaven.generated.tables.pojos.ExternalEvents;
import com.codemaven.generated.tables.pojos.Sessions;
import com.codemaven.generated.tables.records.ExternalEventsRecord;
import com.codemaven.generated.tables.records.SessionsRecord;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class EventsDao
{
	private final DSLContext dsl;

	public List<ExternalEvents> fetchEventsAfterDate(final LocalDateTime after)
	{
		return dsl.selectFrom(Tables.EXTERNAL_EVENTS)
				.where(Tables.EXTERNAL_EVENTS.EVENT_DATE.ge(after))
				.fetchInto(ExternalEvents.class);
	}

	public List<ExternalEvents> fetchEventsBetweenDates(final LocalDateTime before, final LocalDateTime after)
	{
		return dsl.selectFrom(Tables.EXTERNAL_EVENTS)
				.where(Tables.EXTERNAL_EVENTS.EVENT_DATE.ge(before))
				.and(Tables.EXTERNAL_EVENTS.EVENT_DATE.lt(after))
				.fetchInto(ExternalEvents.class);
	}

	public ExternalEvents fetchEventById(final int eventId)
	{
		return dsl.selectFrom(Tables.EXTERNAL_EVENTS)
				.where(Tables.EXTERNAL_EVENTS.ID.eq(eventId))
				.fetchOneInto(ExternalEvents.class);
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
	
	public boolean saveEvent(final ExternalEvents event)
	{
		ExternalEventsRecord record = dsl.newRecord(Tables.EXTERNAL_EVENTS, event);
		return dsl.insertInto(Tables.EXTERNAL_EVENTS).set(record).onDuplicateKeyUpdate().set(record).execute() > 0;
	}
	
	public boolean deleteSessionById(final int sessionId)
	{
		return dsl.deleteFrom(Tables.SESSIONS)
				.where(Tables.SESSIONS.ID.eq(sessionId)).execute() > 0;
	}
}
