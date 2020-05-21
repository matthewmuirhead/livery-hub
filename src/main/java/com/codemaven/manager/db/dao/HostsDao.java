package com.codemaven.manager.db.dao;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.codemaven.generated.Tables;
import com.codemaven.generated.tables.pojos.Hosts;
import com.codemaven.generated.tables.records.HostsRecord;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class HostsDao
{
	private final DSLContext dsl;
	
	public List<Hosts> fetchAllHosts()
	{
		return dsl.selectFrom(Tables.HOSTS).orderBy(Tables.HOSTS.NAME).fetchInto(Hosts.class);
	}
	
	public Hosts fetchHostById(final int id)
	{
		return dsl.selectFrom(Tables.HOSTS).where(Tables.HOSTS.ID.eq(id)).fetchOneInto(Hosts.class);
	}
	
	public boolean saveHost(final Hosts host)
	{
		HostsRecord record = dsl.newRecord(Tables.HOSTS, host);
		return dsl.insertInto(Tables.HOSTS).set(record).onDuplicateKeyUpdate().set(record).execute() > 0;
	}
}
