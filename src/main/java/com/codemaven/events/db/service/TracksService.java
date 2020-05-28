package com.codemaven.events.db.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.testcontainers.shaded.io.netty.util.internal.StringUtil;

import com.codemaven.events.db.Service;
import com.codemaven.events.db.ServiceType;
import com.codemaven.events.db.dao.TracksDao;
import com.codemaven.generated.tables.pojos.Locations;
import com.codemaven.generated.tables.pojos.Tracks;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class TracksService implements Service
{
	private TracksDao dao;
	
	public List<Tracks> fetchAllTracks()
	{
		return dao.fetchAllTracks();
	}
	
	public List<Tracks> fetchTracksBySet(String set)
	{
		List<Tracks> tracks = new ArrayList<>();
		if (!StringUtil.isNullOrEmpty(set))
		{
			tracks = dao.fetchTracksBySet(set);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching tracks with set " + set);
		}
		return tracks;
	}
	
	public Map<Tracks, Locations> fetchTracksLocationsBySet(String set)
	{
		Map<Tracks, Locations> tracks = new HashMap<>();
		if (!StringUtil.isNullOrEmpty(set))
		{
			tracks = dao.fetchTracksLocationsBySet(set);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching tracks with set " + set);
		}
		return tracks;
	}
	
	public Tracks fetchTrackById(final int trackId)
	{
		Tracks track = null;
		if (trackId > 0)
		{
			track = dao.fetchTrackById(trackId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching tracks with id " + trackId);
		}
		return track;
	}
	
	public Locations fetchLocationById(final int locationId)
	{
		Locations location = null;
		if (locationId > 0)
		{
			location = dao.fetchLocationById(locationId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching location with id " + locationId);
		}
		return location;
	}
	
	@Override
	public ServiceType getType()
	{
		return ServiceType.TRACK;
	}
}
