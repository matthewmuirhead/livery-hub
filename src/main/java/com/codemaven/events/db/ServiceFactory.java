package com.codemaven.events.db;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ServiceFactory
{
	private final List<Service> services;

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