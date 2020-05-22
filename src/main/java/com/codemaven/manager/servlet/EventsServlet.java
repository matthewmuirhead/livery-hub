package com.codemaven.manager.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.generated.tables.pojos.Events;
import com.codemaven.generated.tables.pojos.Hosts;
import com.codemaven.generated.tables.pojos.Tracks;
import com.codemaven.manager.db.ServiceFactory;
import com.codemaven.manager.db.ServiceType;
import com.codemaven.manager.db.service.EventsService;
import com.codemaven.manager.db.service.HostsService;
import com.codemaven.manager.db.service.TracksService;
import com.codemaven.manager.enums.NavBarZone;
import com.codemaven.manager.lists.EventDetailsList;
import com.codemaven.manager.model.AjaxSaveReplyJson;
import com.codemaven.manager.model.CarouselDisplayItem;
import com.codemaven.manager.model.EventDetails;
import com.codemaven.manager.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/events")
@Slf4j
public class EventsServlet extends ServletBase
{
	private static final String JSP_PATH = "events";
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
		if (StringUtil.isNullOrEmpty(cmd) || StringUtil.isEqual(cmd, "calendar"))
		{
			doCalendar(req, resp);
		}
		if (StringUtil.isEqual(cmd, "updateCalendar"))
		{
			doCalendarUpdate(req, resp);
		}
		else if (StringUtil.isEqual(cmd, "list"))
		{
			doList(req, resp);
		}
		else if (StringUtil.isEqual(cmd, "view"))
		{
			doView(req, resp);
		}
		else if (StringUtil.isEqual(cmd, "new"))
		{
			doNew(req, resp);
		}
		else if (StringUtil.isEqual(cmd, "edit"))
		{
			doEdit(req, resp);
		}
		else if (StringUtil.isEqual(cmd, "save"))
		{
			doSave(req, resp, false);
		}
		else if (StringUtil.isEqual(cmd, "ajaxSave"))
		{
			doSave(req, resp, true);
		}
	}
	
	private void doCalendar(HttpServletRequest req, HttpServletResponse resp)
	{
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
		
		LocalDateTime from = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0);
		LocalDateTime to = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59);
		List<Events> events = serviceFactory.getInstance(ServiceType.EVENT, EventsService.class).fetchEventsBetweenDates(from, to);
		EventDetailsList eventDetailsList = new EventDetailsList(events, serviceFactory);
		
		req.setAttribute("eventDetails", eventDetailsList);
		req.setAttribute("currentMonth", from.getMonth());
		req.setAttribute("title", "Events Calendar");
		displayPage(req, resp, JSP_PATH+"/calendar.jsp");
	}
	
	private void doCalendarUpdate(HttpServletRequest req, HttpServletResponse resp)
	{
		// Ajax call to change month
	}
	
	private void doList(HttpServletRequest req, HttpServletResponse resp)
	{
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
		
		LocalDateTime from = LocalDateTime.now()
				.withHour(0)
				.withMinute(0)
				.withSecond(0)
				.withNano(0);
		List<Events> events = serviceFactory.getInstance(ServiceType.EVENT, EventsService.class).fetchEventsAfterDate(from);
		EventDetailsList eventDetailsList = new EventDetailsList(events, serviceFactory);
		req.setAttribute("eventDetails", eventDetailsList);
		
		req.setAttribute("title", "Events List");
		displayPage(req, resp, JSP_PATH+"/list.jsp");
	}
	
	private void doView(HttpServletRequest req, HttpServletResponse resp)
	{
		int eventId = getParameterInt(req, "id");
		doView(req, resp, eventId);
	}
	
	private void doView(HttpServletRequest req, HttpServletResponse resp, int id)
	{
		int eventId = getParameterInt(req, "id");
		EventDetails eventDetails = new EventDetails(serviceFactory, eventId);
		eventDetails.populate();
		req.setAttribute("eventDetails", eventDetails);
		req.setAttribute("isIGTC", StringUtil.isEqual("IGTC", eventDetails.getTrack().getSet()));
		req.setAttribute("title", "View "+eventDetails.getEvent().getName());
		displayPage(req, resp, JSP_PATH+"/view.jsp");
	}
	
	private void doNew(HttpServletRequest req, HttpServletResponse resp)
	{
		Events event = new Events();
		event.setName("New Event");
		event.setEventDate(LocalDateTime.now().withSecond(0).withNano(0));
		req.setAttribute("event", event);
		
		List<Tracks> tracks = serviceFactory.getInstance(ServiceType.TRACK, TracksService.class).fetchAllTracks();
		req.setAttribute("tracks", tracks);
		
		List<Hosts> hosts = serviceFactory.getInstance(ServiceType.HOST, HostsService.class).fetchAllHosts();
		req.setAttribute("hosts", hosts);
		
		req.setAttribute("title", "New Event");
		displayPage(req, resp, JSP_PATH+"/edit.jsp");
	}
	
	private void doEdit(HttpServletRequest req, HttpServletResponse resp)
	{
		int eventId = getParameterInt(req, "id");
		EventDetails eventDetails = new EventDetails(serviceFactory, eventId);
		eventDetails.populate();
		req.setAttribute("eventDetails", eventDetails);
		
		List<Tracks> tracks = serviceFactory.getInstance(ServiceType.TRACK, TracksService.class).fetchAllTracks();
		req.setAttribute("tracks", tracks);
		
		List<Hosts> hosts = serviceFactory.getInstance(ServiceType.HOST, HostsService.class).fetchAllHosts();
		req.setAttribute("hosts", hosts);
		
		req.setAttribute("title", "Edit "+eventDetails.getEvent().getName());
		displayPage(req, resp, JSP_PATH+"/edit.jsp");
	}
	
	private void doSave(HttpServletRequest req, HttpServletResponse resp, boolean isAjax) throws IOException
	{
		AjaxSaveReplyJson replyJson = new AjaxSaveReplyJson();
		Events event = populateEventFromRequest(req);
		EventDetails eventDetails = new EventDetails(serviceFactory, event);
		boolean saved = eventDetails.save();
		if (isAjax)
		{
			if (saved)
			{
				// Success Ajax Response
				replyJson.addErrorMessage(AjaxSaveReplyJson.SUCCESS_KEY, "Event " + event.getName() + " saved successfully.");
				resp.setContentType("application/json");
				resp.getWriter().write(replyJson.toJsonString());
			}
			else
			{
				// Failed Ajax Response
				replyJson.addErrorMessage(AjaxSaveReplyJson.GENERAL_ERROR, "Could not save event " + event.getName() + ".");
				resp.setContentType("application/json");
				resp.getWriter().write(replyJson.toJsonString());
			}
		}
		else
		{
			if (saved)
			{
				displayPage(req, resp, JSP_PATH, true);
			}
			else
			{
				req.setAttribute(AjaxSaveReplyJson.GENERAL_ERROR, "Could not save event " + event.getName() + ".");
				doView(req, resp, event.getId());
			}
		}
	}
	
	private Events populateEventFromRequest(HttpServletRequest req)
	{
		Events event = new Events();
		event.setId(getParameterInt(req, "eventId"));
		event.setName(getParameterString(req, "name"));
		event.setDescription(getParameterString(req, "description"));
		event.setRegulations(getParameterString(req, ""));
		event.setHostId(getParameterInt(req, "hostId"));
		event.setTrackId(getParameterInt(req, "trackId"));
		event.setEventDate(LocalDateTime.parse(getParameterString(req, "eventDate")));
		return event;
	}
	
	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.EVENTS;
	}
}