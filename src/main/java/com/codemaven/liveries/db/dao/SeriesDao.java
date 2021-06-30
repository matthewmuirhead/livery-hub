package com.codemaven.liveries.db.dao;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.codemaven.generated.tables.pojos.Series

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class SeriesDao
{
	private final DSLContext dsl;

	public List<Series> fetchAllSeries()
	{
		return dsl.selectFrom(Tables.SERIES)
				.fetchInto(Series.class);
	}
	
	public Series fetchSeriesById(final int seriesId)
	{
		return dsl.selectFrom(Tables.SERIES)
				.where(Tables.SERIES.ID.eq(seriesId))
				.fetchOneInto();
	}
	
	public boolean saveSeries(final Series team)
	{
		SeriesRecord record = dsl.newRecord(Tables.SERIES, team);
		return dsl.insertInto(Tables.SERIES)
				.set(record)
				.onDuplicateKeyUpdate()
				.set(record)
				.execute() > 0;
	}

	public boolean deleteSeriesById(final int seriesId)
	{
		return dsl.deleteFrom(Tables.SERIES)
				.where(Tables.SERIES.ID.eq(seriesId))
				.execute() > 0;
	}
}
