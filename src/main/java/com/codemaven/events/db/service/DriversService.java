package com.codemaven.events.db.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.codemaven.events.db.Service;
import com.codemaven.events.db.ServiceType;
import com.codemaven.events.db.dao.DriversDao;
import com.codemaven.generated.tables.pojos.Drivers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class DriversService implements Service
{
	private DriversDao dao;
	
	public List<Drivers> fetchAllDrivers()
	{
		return dao.fetchAllDrivers();
	}
	
	public Drivers fetchDriverById(final int driverId)
	{
		Drivers driver = null;
		if (driverId > 0)
		{
			driver = dao.fetchDriverById(driverId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching car with id " + driverId);
		}
		return driver;
	}
	
	public boolean saveDriver(final Drivers driver)
	{
		boolean saved = false;
		if (driver != null)
		{
			saved = dao.saveDriver(driver);
		}
		else
		{
			log.debug("Tried saving null car");
		}
		return saved;
	}
	
	@Override
	public ServiceType getType()
	{
		return ServiceType.DRIVER;
	}
}
