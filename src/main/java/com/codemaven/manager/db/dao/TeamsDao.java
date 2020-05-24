package com.codemaven.manager.db.dao;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.codemaven.generated.Tables;
import com.codemaven.generated.tables.pojos.TeamFuel;
import com.codemaven.generated.tables.pojos.TeamTires;
import com.codemaven.generated.tables.pojos.Teams;
import com.codemaven.generated.tables.records.TeamsRecord;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class TeamsDao
{
	private final DSLContext dsl;

	public List<Teams> fetchEventsTeams(final int eventId)
	{
		return dsl.selectFrom(Tables.TEAMS)
				.where(Tables.TEAMS.EVENT_ID.eq(eventId))
				.fetchInto(Teams.class);
	}
	
	public Teams fetchTeamById(final int teamId)
	{
		return dsl.selectFrom(Tables.TEAMS)
				.where(Tables.TEAMS.ID.eq(teamId))
				.fetchOneInto(Teams.class);
	}
	
	public List<Teams> fetchAllTeams()
	{
		return dsl.selectFrom(Tables.TEAMS)
				.orderBy(Tables.TEAMS.NAME)
				.fetchInto(Teams.class);
	}
	
	public List<TeamTires> fetchTeamTiresByTeamId(final int teamId)
	{
		return dsl.selectFrom(Tables.TEAM_TIRES)
				.where(Tables.TEAM_TIRES.TEAM_ID.eq(teamId))
				.fetchInto(TeamTires.class);
	}
	
	public List<TeamFuel> fetchTeamFuelByTeamId(final int teamId)
	{
		return dsl.selectFrom(Tables.TEAM_FUEL)
				.where(Tables.TEAM_FUEL.TEAM_ID.eq(teamId))
				.fetchInto(TeamFuel.class);
	}
	
	public boolean saveTeam(final Teams team)
	{
		TeamsRecord record = dsl.newRecord(Tables.TEAMS, team);
		return dsl.insertInto(Tables.TEAMS).set(record).onDuplicateKeyUpdate().set(record).execute() > 0;
	}
	
	public boolean deleteTeamTiresByTeamId(final int teamId)
	{
		return dsl.deleteFrom(Tables.TEAM_TIRES)
				.where(Tables.TEAM_TIRES.TEAM_ID.eq(teamId))
				.execute() > 0;
	}
	
	public boolean deleteTeamFuelByTeamId(final int teamId)
	{
		return dsl.deleteFrom(Tables.TEAM_FUEL)
				.where(Tables.TEAM_FUEL.TEAM_ID.eq(teamId))
				.execute() > 0;
	}
	
	public boolean deleteTeamById(final int teamId)
	{
		return dsl.deleteFrom(Tables.TEAMS)
				.where(Tables.TEAMS.ID.eq(teamId))
				.execute() > 0;
	}
}
