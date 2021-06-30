package com.codemaven.events.db.service;

import org.springframework.stereotype.Component;

import com.codemaven.events.db.Service;
import com.codemaven.events.db.ServiceType;
import com.codemaven.events.db.dao.UsersDao;
import com.codemaven.events.util.StringUtil;
import com.codemaven.generated.tables.pojos.Users;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class UsersService implements Service
{
	private UsersDao dao;
	
	public Users fetchUserByUsername(final String username)
	{
		Users user = null;
		if (!StringUtil.isNullOrEmpty(username))
		{
			user = dao.fetchUserByUsername(username);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching user with username: " + username);
		}
		return user;
	}
	
	@Override
	public ServiceType getType()
	{
		return ServiceType.USER;
	}
}
