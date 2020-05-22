package com.codemaven.manager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.generated.tables.pojos.Cars;
import com.codemaven.manager.db.ServiceFactory;
import com.codemaven.manager.db.ServiceType;
import com.codemaven.manager.db.service.CarsService;
import com.codemaven.manager.enums.NavBarZone;
import com.codemaven.manager.model.AjaxSaveReplyJson;
import com.codemaven.manager.model.CarouselDisplayItem;
import com.codemaven.manager.model.CarsExtended;
import com.codemaven.manager.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/cars")
@Slf4j
public class CarsServlet extends ServletBase
{
	private static final String JSP_PATH = "cars";
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
	
	public void doCmd(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String cmd = getCmd(req);
		if (StringUtil.isNullOrEmpty(cmd) || StringUtil.isEqual(cmd, "list"))
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
		else if (StringUtil.isEqual(cmd, "save"))
		{
			doSave(req, resp, false);
		}
		else if (StringUtil.isEqual(cmd, "ajaxSave"))
		{
			doSave(req, resp, true);
		}
	}
	
	private void doList(HttpServletRequest req, HttpServletResponse resp)
	{
		List<CarsExtended> cars = serviceFactory.getInstance(ServiceType.CAR, CarsService.class).fetchAllCarsSorted();
		log.debug(cars.size() + " cars loaded");
		req.setAttribute("cars", cars);
		
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
		
		req.setAttribute("title", "Cars");
		displayPage(req, resp, JSP_PATH+"/list.jsp");
	}
	
	private void doView(HttpServletRequest req, HttpServletResponse resp)
	{
		doView(req, resp, getParameterInt(req, "id"));
	}
	
	private void doView(HttpServletRequest req, HttpServletResponse resp, int carId)
	{
		CarsExtended car = serviceFactory.getInstance(ServiceType.CAR, CarsService.class).fetchCarById(carId);
		if (car != null)
		{
			req.setAttribute("car", car);
			req.setAttribute("title", car.getFullNameAndYear());
			displayPage(req, resp, JSP_PATH+"/view.jsp");
		}
		else
		{
			doList(req, resp);
		}
	}
	
	private void doNew(HttpServletRequest req, HttpServletResponse resp)
	{
		CarsExtended car = new CarsExtended();
		req.setAttribute("car", car);
		req.setAttribute("title", "New Car");
		displayPage(req, resp, JSP_PATH+"/view.jsp");
	}
	
	private void doSave(HttpServletRequest req, HttpServletResponse resp, boolean isAjax) throws IOException
	{
 		AjaxSaveReplyJson replyJson = new AjaxSaveReplyJson();
 		CarsExtended car = new CarsExtended();
		car.setId(getParameterInt(req, "carId"));
		car.setManufacturer(getParameterString(req, "manufacturer"));
		car.setModel(getParameterString(req, "model"));
		car.setYear(getParameterInt(req, "year"));
		car.setLogo(getParameterString(req, "logo"));
		car.setImage(getParameterString(req, "image"));
		boolean saved = serviceFactory.getInstance(ServiceType.CAR, CarsService.class).saveCar(car);
		if (isAjax)
		{
			if (saved)
			{
				// Success Ajax Response
				replyJson.addErrorMessage(AjaxSaveReplyJson.SUCCESS_KEY, "Car " + car.getFullName() + " saved successfully.");
				resp.setContentType("application/json");
				resp.getWriter().write(replyJson.toJsonString());
			}
			else
			{
				// Failed Ajax Response
				replyJson.addErrorMessage(AjaxSaveReplyJson.GENERAL_ERROR, "Could not save car " + car.getFullName() + ".");
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
				req.setAttribute(AjaxSaveReplyJson.GENERAL_ERROR, "Could not save car " + car.getFullName() + ".");
				doView(req, resp, car.getId());
			}
		}
	}

	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.CARS;
	}
}
