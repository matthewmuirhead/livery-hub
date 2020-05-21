package com.codemaven.manager.db.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.codemaven.generated.tables.pojos.Hosts;
import com.codemaven.manager.db.Service;
import com.codemaven.manager.db.ServiceType;
import com.codemaven.manager.db.dao.HostsDao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class HostsService implements Service
{
	private HostsDao dao;
	
	public List<Hosts> fetchAllHosts()
	{
		return dao.fetchAllHosts();
	}
	
	public Hosts fetchHostById(final int hostId)
	{
		Hosts host = null;
		if (hostId > 0)
		{
			host = dao.fetchHostById(hostId);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried fetching car with id " + hostId);
		}
		return host;
	}
	
	public boolean saveHost(final Hosts host)
	{
		boolean saved = false;
		if (host != null)
		{
			saved = dao.saveHost(host);
		}
		else
		{
			log.debug("Tried saving null car");
		}
		return saved;
	}
	
	@Override
	public ServiceType getType()
	{
		return ServiceType.HOST;
	}
}
