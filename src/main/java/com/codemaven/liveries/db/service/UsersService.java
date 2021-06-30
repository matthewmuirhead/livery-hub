package com.codemaven.liveries.db.service;

import org.springframework.stereotype.Component;

import com.codemaven.generated.tables.pojos.Users;
import com.codemaven.liveries.db.Service;
import com.codemaven.liveries.db.ServiceType;
import com.codemaven.liveries.db.dao.UsersDao;
import com.codemaven.liveries.util.StringUtil;

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
