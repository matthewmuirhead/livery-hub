package com.codemaven.liveries.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.liveries.enums.NavBarZone;
import com.codemaven.liveries.servlet.ServletBase;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/404")
public class ServletNotFound extends ServletBase
{
	private static final String ERROR_PAGE_JSP = "errors/404.jsp";
	private static final String ERROR_PAGE_TITLE = "title";
	private static final String ERROR_PAGE_TITLE_VALUE = "404 - Page Not Found";

	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		req.setAttribute(ERROR_PAGE_TITLE, ERROR_PAGE_TITLE_VALUE);
		displayPage(req, resp, ERROR_PAGE_JSP);
	}

	@Override
	protected NavBarZone getNavBarZone()
	{
		return null;
	}
}