package com.codemaven.manager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.generated.tables.pojos.Hosts;
import com.codemaven.manager.db.ServiceFactory;
import com.codemaven.manager.db.ServiceType;
import com.codemaven.manager.db.service.HostsService;
import com.codemaven.manager.enums.NavBarZone;
import com.codemaven.manager.model.CarouselDisplayItem;
import com.codemaven.manager.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/hosts")
@Slf4j
public class HostsServlet extends ServletBase
{
	private static final String JSP_PATH = "hosts";
	private ServiceFactory serviceFactory;
	
	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		try {
			doCmd(req, resp);
		}
		catch (Exception e)
		{
			log.error("Error processing cmd: " + e.getMessage(), e);
			displayError(req, resp, "Uh-Oh, something went wrong!");
		}
	}
	
	private void doCmd(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String cmd = getCmd(req);
		if (StringUtil.isNullOrEmpty(cmd) || StringUtil.isEqual(cmd, "list")) {
			doList(req, resp);
		}
	}
	
	private void doList(HttpServletRequest req, HttpServletResponse resp)
	{
		List<Hosts> hosts = serviceFactory.getInstance(ServiceType.HOST, HostsService.class).fetchAllHosts();
		req.setAttribute("hosts", hosts);
		
		List<CarouselDisplayItem> carousel = new ArrayList<>();
		CarouselDisplayItem displayItem = new CarouselDisplayItem();
		displayItem.setAltText("Audi R8 Tail");
		displayItem.setUrl("audi-tail.png");
		carousel.add(displayItem);
		CarouselDisplayItem displayItem2 = new CarouselDisplayItem();
		displayItem2.setAltText("Nissan GTR Tail");
		displayItem2.setUrl("gtr-tail-pits.png");
		carousel.add(displayItem2);
		req.setAttribute("carousel", carousel);
		
		req.setAttribute("title", "Hosts");
		displayPage(req, resp, JSP_PATH+"/list.jsp");
	}
	
	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.HOSTS;
	}
}
