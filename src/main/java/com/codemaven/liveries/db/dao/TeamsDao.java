package com.codemaven.liveries.db.dao;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.codemaven.generated.Tables;
import com.codemaven.generated.tables.pojos.Teams;
import com.codemaven.generated.tables.records.TeamsRecord;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class TeamsDao
{
	private final DSLContext dsl;

	public List<Teams> fetchSeriesTeams(final int seriesId)
	{
		return dsl.selectFrom(Tables.TEAMS)
				.where(Tables.TEAMS.SERIES_ID.eq(seriesId))
				.fetchInto(Teams.class);
	}

	public Teams fetchTeamById(final int teamId)
	{
		return dsl.selectFrom(Tables.TEAMS)
				.where(Tables.TEAMS.ID.eq(teamId))
				.fetchOneInto(Teams.class);
	}

	public boolean saveTeam(final Teams team)
	{
		TeamsRecord record = dsl.newRecord(Tables.TEAMS, team);
		return dsl.insertInto(Tables.TEAMS)
				.set(record)
				.onDuplicateKeyUpdate()
				.set(record)
				.execute() > 0;
	}

	public boolean deleteTeamByIdAndUser(final int teamId, final int userId)
	{
		return dsl.deleteFrom(Tables.TEAMS.join(Tables.USERS_TEAMS)
				.on(Tables.USERS_TEAMS.TEAM_ID.eq(Tables.TEAMS.ID)))
				.where(Tables.TEAMS.ID.eq(teamId))
				.and(Tables.USERS_TEAMS.USER_ID.eq(userId))
				.execute() > 0;
	}

	public List<Teams> fetchTeamsByUserId(final int userId)
	{
		return dsl.selectFrom(Tables.TEAMS)
				.where(Tables.TEAMS.SERIES_ID.eq(userId))
				.fetchInto(Teams.class);
	}
}
