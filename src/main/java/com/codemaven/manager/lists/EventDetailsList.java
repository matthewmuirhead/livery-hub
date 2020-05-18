package com.codemaven.manager.lists;

import java.util.ArrayList;
import java.util.List;

import com.codemaven.generated.tables.pojos.Events;
import com.codemaven.manager.db.ServiceFactory;
import com.codemaven.manager.model.EventDetails;

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
}
