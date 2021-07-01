package com.codemaven.liveries.db.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.codemaven.generated.Tables;
import com.codemaven.generated.tables.pojos.Users;
import com.codemaven.generated.tables.records.UsersRecord;
import com.codemaven.liveries.model.ExtendedUser;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class UsersDao
{
	private final DSLContext dsl;
	
	public ExtendedUser fetchUserByUsername(final String username)
	{
		return dsl.selectFrom(Tables.USERS)
				.where(Tables.USERS.USERNAME.eq(username))
				.fetchOneInto(ExtendedUser.class);
	}
	
	public boolean userExists(final String username)
	{
		return dsl.fetchExists(dsl.select(Tables.USERS.ID)
				.from(Tables.USERS)
				.where(Tables.USERS.USERNAME.eq(username)));
	}
	
	public boolean saveUser(final Users user)
	{
		UsersRecord record = dsl.newRecord(Tables.USERS, user);
		return dsl.insertInto(Tables.USERS)
				.set(record)
				.onDuplicateKeyUpdate()
				.set(record)
				.execute() > 0;
	}
}
