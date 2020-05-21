package com.codemaven.manager.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.generated.tables.pojos.Locations;
import com.codemaven.generated.tables.pojos.Tracks;
import com.codemaven.manager.db.ServiceFactory;
import com.codemaven.manager.db.ServiceType;
import com.codemaven.manager.db.service.TracksService;
import com.codemaven.manager.enums.NavBarZone;
import com.codemaven.manager.model.CarouselDisplayItem;
import com.codemaven.manager.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/tracks")
@Slf4j
public class TracksServlet extends ServletBase
{
	private ServiceFactory serviceFactory;
	
	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		String cmd = getCmd(req);
		if (StringUtil.isNullOrEmpty(cmd) || StringUtil.isEqual(cmd, "list")) {
			doList(req, resp);
		}
	}
	
	private void doList(HttpServletRequest req, HttpServletResponse resp)
	{
		TracksService service = serviceFactory.getInstance(ServiceType.TRACK, TracksService.class);

		Map<Tracks, Locations> igtc = service.fetchTracksLocationsBySet("IGTC");
		log.info(igtc.size() + " IGTC tracks loaded");
		req.setAttribute("igtc", igtc);
		
		Map<Tracks, Locations> gtwc2019 = service.fetchTracksLocationsBySet("GTWC 2019");
		log.info(gtwc2019.size() + " GTWC 2019 tracks loaded");
		req.setAttribute("gtwc2019", gtwc2019);
		
		Map<Tracks, Locations> gtwc2018 = service.fetchTracksLocationsBySet("GTWC 2018");
		log.info(gtwc2018.size() + " GTWC 2018 tracks loaded");
		req.setAttribute("gtwc2018", gtwc2018);
		
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
		
		req.setAttribute("title", "Tracks");
		displayPage(req, resp, "tracks/list.jsp");
	}

	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.TRACKS;
	}
}
