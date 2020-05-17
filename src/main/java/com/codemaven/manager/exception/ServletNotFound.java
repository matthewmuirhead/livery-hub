package com.codemaven.manager.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.manager.enums.NavBarZone;
import com.codemaven.manager.servlet.ServletBase;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/404")
public class ServletNotFound extends ServletBase
{
	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		req.setAttribute("title", "404 - Page Not Found");
		displayPage(req, resp, "errors/404.jsp");
	}

	@Override
	protected NavBarZone getNavBarZone()
	{
		return null;
	}
}