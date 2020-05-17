package com.codemaven.manager.db.dao;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.codemaven.generated.Tables;
import com.codemaven.generated.tables.pojos.Cars;
import com.codemaven.generated.tables.records.CarsRecord;
import com.codemaven.manager.model.CarsExtended;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class CarDao
{
	private final DSLContext dsl;
	
	public List<CarsExtended> fetchAllCarsSorted()
	{
		return dsl.selectFrom(Tables.CARS).orderBy(Tables.CARS.MANUFACTURER, Tables.CARS.YEAR).fetchInto(CarsExtended.class);
	}
	
	public CarsExtended fetchCarById(final int id)
	{
		return dsl.selectFrom(Tables.CARS).where(Tables.CARS.ID.eq(id)).fetchOneInto(CarsExtended.class);
	}
	
	public boolean saveCar(final Cars car)
	{
		CarsRecord record = dsl.newRecord(Tables.CARS, car);
		return dsl.insertInto(Tables.CARS).set(record).onDuplicateKeyUpdate().set(record).execute() > 0;
	}
}
