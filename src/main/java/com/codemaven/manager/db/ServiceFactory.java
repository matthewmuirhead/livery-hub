package com.codemaven.manager.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.codemaven.manager.db.service.CarService;
import com.codemaven.manager.db.service.EventService;
import com.codemaven.manager.db.service.TeamService;
import com.codemaven.manager.db.service.TrackService;

@Configuration
public class ServiceFactory
{
	private final List<Service> services;

	@Autowired
	public ServiceFactory(CarService carService, EventService eventService, TeamService teamService, TrackService trackService)
	{
		this.services = new ArrayList<>();
		this.services.add(carService);
		this.services.add(eventService);
		this.services.add(teamService);
		this.services.add(trackService);
	}

	public <T extends Service> T getInstance(final ServiceType type, final Class<T> cls)
	{
		Service service = null;
		if (type != null && cls != null)
		{
			service = services.stream()
					.filter(p -> p.getType() == type && cls.isInstance(p))
					.findFirst()
					.orElse(null);
		}
		return service == null ? null : cls.cast(service);
	}
}