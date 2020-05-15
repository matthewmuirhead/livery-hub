package com.codemaven.manager.db.dao;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.codemaven.generated.Tables;
import com.codemaven.generated.tables.pojos.Cars;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class CarDao
{
	private final DSLContext dsl;
	
	public List<Cars> fetchAllCars()
	{
		return dsl.selectFrom(Tables.CARS).fetchInto(Cars.class);
	}
}
