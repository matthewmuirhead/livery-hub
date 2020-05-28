package com.codemaven.events.manager.lists;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.codemaven.events.db.ServiceFactory;
import com.codemaven.events.manager.model.ExternalEventDetails;
import com.codemaven.generated.tables.pojos.ExternalEvents;

public class EventDetailsList extends ArrayList<ExternalEventDetails>
{
	private static final long serialVersionUID = 1L;

	public EventDetailsList()
	{
		super();
	}
	
	public EventDetailsList(List<ExternalEvents> events, ServiceFactory serviceFactory)
	{
		super();
		for (ExternalEvents event : events)
		{
			ExternalEventDetails details = new ExternalEventDetails(serviceFactory, event);
			this.add(details);
		}
	}
	
	public void setCalendarStart(LocalDateTime calendarStart)
	{
		for (ExternalEventDetails event : this)
		{
			event.setCalendarStart(calendarStart);
		}
	}
	
	public String toCalendarJson()
	{
		String delimiter = "";
		String json = "\"events\":[";
		for (ExternalEventDetails event : this)
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
