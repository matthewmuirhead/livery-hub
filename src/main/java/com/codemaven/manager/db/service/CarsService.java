package com.codemaven.manager.db.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.codemaven.generated.tables.pojos.Cars;
import com.codemaven.manager.db.Service;
import com.codemaven.manager.db.ServiceType;
import com.codemaven.manager.db.dao.CarsDao;
import com.codemaven.manager.model.CarsExtended;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class CarsService implements Service
{
	private CarsDao dao;
	
	public List<CarsExtended> fetchAllCarsSorted()
	{
		return dao.fetchAllCarsSorted();
	}
	
	public CarsExtended fetchCarById(final int carId)
	{
		CarsExtended car = null;
		if (carId > 0)
		{
			car = dao.fetchCarById(carId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching car with id " + carId);
		}
		return car;
	}
	
	public boolean saveCar(final Cars car)
	{
		boolean saved = false;
		if (car != null)
		{
			saved = dao.saveCar(car);
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
		return ServiceType.CAR;
	}
}
