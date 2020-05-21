package com.codemaven.manager.db.dao;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.codemaven.generated.Tables;
import com.codemaven.generated.tables.pojos.Drivers;
import com.codemaven.generated.tables.records.DriversRecord;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class DriversDao
{
	private final DSLContext dsl;
	
	public List<Drivers> fetchAllDrivers()
	{
		return dsl.selectFrom(Tables.DRIVERS).orderBy(Tables.DRIVERS.NAME).fetchInto(Drivers.class);
	}
	
	public Drivers fetchDriverById(final int id)
	{
		return dsl.selectFrom(Tables.DRIVERS).where(Tables.DRIVERS.ID.eq(id)).fetchOneInto(Drivers.class);
	}
	
	public boolean saveDriver(final Drivers driver)
	{
		DriversRecord record = dsl.newRecord(Tables.DRIVERS, driver);
		return dsl.insertInto(Tables.DRIVERS).set(record).onDuplicateKeyUpdate().set(record).execute() > 0;
	}
}
