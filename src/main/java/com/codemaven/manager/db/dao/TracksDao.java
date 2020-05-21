package com.codemaven.manager.db.dao;

import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.codemaven.generated.Tables;
import com.codemaven.generated.tables.pojos.Locations;
import com.codemaven.generated.tables.pojos.Tracks;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class TracksDao
{
	private final DSLContext dsl;

	public List<Tracks> fetchAllTracks()
	{
		return dsl.selectFrom(Tables.TRACKS)
				.fetchInto(Tracks.class);
	}

	public List<Tracks> fetchTracksBySet(String set)
	{
		return dsl.selectFrom(Tables.TRACKS)
				.where(Tables.TRACKS.SET.eq(set))
				.fetchInto(Tracks.class);
	}

	public Map<Tracks, Locations> fetchTracksLocationsBySet(String set)
	{
		return dsl.selectFrom(Tables.TRACKS.join(Tables.LOCATIONS)
				.on(Tables.TRACKS.LOCATION_ID.eq(Tables.LOCATIONS.ID)))
				.where(Tables.TRACKS.SET.eq(set))
				.fetchMap(Tracks.class, Locations.class);
	}

	public Tracks fetchTrackById(final int trackId)
	{
		return dsl.selectFrom(Tables.TRACKS)
				.where(Tables.TRACKS.ID.eq(trackId))
				.fetchOneInto(Tracks.class);
	}
}
