package com.codemaven.manager.db.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.codemaven.generated.tables.pojos.Cars;
import com.codemaven.manager.db.Service;
import com.codemaven.manager.db.ServiceType;
import com.codemaven.manager.db.dao.CarDao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class CarService implements Service
{
	private CarDao dao;
	
	public List<Cars> fetchAllCars()
	{
		return dao.fetchAllCars();
	}
	
	@Override
	public ServiceType getType()
	{
		return ServiceType.CAR;
	}

}
