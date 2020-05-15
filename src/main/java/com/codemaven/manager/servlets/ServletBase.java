package com.codemaven.manager.servlets;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class ServletBase
{
	@GetMapping
	public ModelAndView doGet(HttpServletRequest req, HttpServletResponse resp)
	{
		try
		{
			return processCmd(req, resp);
		}
		catch (Exception e)
		{
			log.error("Uncaught Exception: " + e.getMessage(), e);
			return handleException();
		}
	}
	
	@PostMapping
	public ModelAndView doPost(HttpServletRequest req, HttpServletResponse resp)
	{
		try
		{
			return processCmd(req, resp);
		}
		catch (Exception e)
		{
			log.error("Uncaught Exception: " + e.getMessage(), e);
			return handleException();
		}
	}
	
	protected ModelAndView processCmd(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		// Overriden in subclass
		throw new Exception("Accessed a page with no processCmd() setup");
	}
	
	protected String getParameterString(final HttpServletRequest req, final String key)
	{
		String value = null;
		Object o = req.getParameter(key);
		if (o instanceof String)
		{
			value = (String) o;
		}
		return value;
	}
	
	protected int getParameterInt(final HttpServletRequest req, final String key)
	{
		int value = 0;
		Object o = req.getParameter(key);
		if (o instanceof Integer)
		{
			value = (int) o;
		}
		return value;
	}
	
	protected String getCmd(final HttpServletRequest req)
	{
		return getParameterString(req, "cmd");
	}
	
	public ModelAndView handleException()
	{
		return handleException("", "418", "I'm a teapot");
	}
	
	public ModelAndView handleException(final String image, final String title, final String message)
	{
		Map<String, String> errors = new HashMap<>();
		errors.put("image", image);
		errors.put("title", title);
		errors.put("message", message);
		return new ModelAndView("error/418", "errors", errors);
	}
}
