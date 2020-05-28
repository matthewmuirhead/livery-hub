package com.codemaven.events.db.dao;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.codemaven.generated.Tables;
import com.codemaven.generated.tables.pojos.Users;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class UsersDao
{
	private final DSLContext dsl;
	
	public Users fetchUserByUsername(final String username)
	{
		return dsl.selectFrom(Tables.USERS)
				.where(Tables.USERS.USERNAME.eq(username))
				.fetchOneInto(Users.class);
	}
}
