package com.codemaven.liveries.db.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.codemaven.generated.tables.pojos.Series;
import com.codemaven.liveries.db.Service;
import com.codemaven.liveries.db.ServiceType;
import com.codemaven.liveries.db.dao.SeriesDao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class SeriesService implements Service
{
	private SeriesDao dao;

	public List<Series> fetchAllSeries()
	{
		return dao.fetchAllSeries();
	}

	public Series fetchSeriesById(final int seriesId)
	{
		Series series = null;
		if (seriesId > 0)
		{
			series = dao.fetchSeriesById(seriesId);
		}
		else
		{
			log.debug("Tried fetching series with invalid Id");
		}
		return series;
	}
	
	public boolean saveSeries(final Series series)
	{
		boolean saved = false;
		if (series != null)
		{
			saved = dao.saveSeries(series);
		}
		else
		{
			log.debug("Tried saving null series");
		}
		return saved;
	}

	public boolean deleteSeriesById(final int seriesId)
	{
		boolean saved = false;
		if (seriesId > 0)
		{
			saved = dao.deleteSeriesById(seriesId);
		}
		else
		{
			log.debug("Tried deleting series with invalid Id");
		}
		return saved;
	}

	@Override
	public ServiceType getType()
	{
		return ServiceType.SERIES;
	}
}
