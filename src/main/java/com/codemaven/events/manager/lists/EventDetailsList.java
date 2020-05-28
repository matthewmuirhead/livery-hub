package com.codemaven.events.manager.lists;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.codemaven.events.db.ServiceFactory;
import com.codemaven.events.model.EventDetails;
import com.codemaven.generated.tables.pojos.Events;

public class EventDetailsList extends ArrayList<EventDetails>
{
	private static final long serialVersionUID = 1L;

	public EventDetailsList()
	{
		super();
	}
	
	public EventDetailsList(List<Events> events, ServiceFactory serviceFactory)
	{
		super();
		for (Events event : events)
		{
			EventDetails details = new EventDetails(serviceFactory, event);
			this.add(details);
		}
	}
	
	public void setCalendarStart(LocalDateTime calendarStart)
	{
		for (EventDetails event : this)
		{
			event.setCalendarStart(calendarStart);
		}
	}
	
	public String toCalendarJson()
	{
		String delimiter = "";
		String json = "\"events\":[";
		for (EventDetails event : this)
		{
			String eventJson = delimiter+"{";
			eventJson += "\"id\":\""+event.getEventId()+"\",";
			eventJson += "\"name\":\""+event.getEvent().getName()+"\",";
			eventJson += "\"display\":\""+event.getCalendarDisplay()+"\",";
			LocalDateTime eventDate = event.getEvent().getEventDate();
			eventJson += "\"time\":\""+eventDate.getHour()+":"+eventDate.getMinute()+"\",";
			eventJson += "\"location\":\""+event.getTrack().getName()+"\"";
			eventJson += "}";
			json += eventJson;
			delimiter = ", ";
		}
		json += "]";
		return json;
	}
}
