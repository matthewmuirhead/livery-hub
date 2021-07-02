package com.codemaven.liveries.db.service;

import org.springframework.stereotype.Component;

import com.codemaven.generated.tables.pojos.Users;
import com.codemaven.liveries.db.Service;
import com.codemaven.liveries.db.ServiceType;
import com.codemaven.liveries.db.dao.UsersDao;
import com.codemaven.liveries.model.ExtendedUser;
import com.codemaven.liveries.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class UsersService implements Service
{
	private UsersDao dao;
	
	public ExtendedUser fetchUserByUsername(final String username)
	{
		ExtendedUser user = null;
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
	
	public boolean userExists(final String username)
	{
		boolean userExists = false;
		if (!StringUtil.isNullOrEmpty(username))
		{
			userExists = dao.userExists(username);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching user with username: " + username);
		}
		return userExists;
	}
	
	public boolean saveUser(final Users user)
	{
		boolean saved = false;
		if (user != null)
		{
			saved = dao.saveUser(user);
		}
		else
		{
			log.debug("Tried saving null user");
		}
		return saved;
	}
	
	@Override
	public ServiceType getType()
	{
		return ServiceType.USER;
	}
}
