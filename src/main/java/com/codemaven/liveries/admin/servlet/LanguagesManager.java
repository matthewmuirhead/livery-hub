package com.codemaven.liveries.admin.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codemaven.generated.tables.pojos.Languages;
import com.codemaven.liveries.db.ServiceFactory;
import com.codemaven.liveries.db.ServiceType;
import com.codemaven.liveries.db.service.LanguagesService;
import com.codemaven.liveries.manager.enums.NavBarZone;
import com.codemaven.liveries.model.ExtendedUser;
import com.codemaven.liveries.servlet.ServletBase;
import com.codemaven.liveries.util.HTMLUtil;
import com.codemaven.liveries.util.StringUtil;
import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/admin/languages")
@Slf4j
public class LanguagesManager extends ServletBase
{
	private static final String JSP_PATH = "languages/";
	private ServiceFactory serviceFactory;

	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse resp)
	{
		try
		{
			ExtendedUser user = getLoggedInUser(req);
			if (user == null || !user.getAdmin())
			{
				// Redirect to homepage
				displayPage(req, resp, "/", true);
			}
			else
			{
				doCmd(req, resp);
			}
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
		if (StringUtil.isEqual(cmd, "translations"))
		{
			doListTranslations(req, resp);
		}
		else if (StringUtil.isEqual(cmd, "saveTranslation"))
		{
			doSaveTranslation(req, resp);
		}
		else
		{
			doList(req, resp);
		}
	}

	private void doList(HttpServletRequest req, HttpServletResponse resp)
	{
		displayPage(req, resp, JSP_PATH + "list.jsp");
	}

	private void doListTranslations(HttpServletRequest req, HttpServletResponse resp)
	{
		int languageId = getParameterInt(req, "languageId");
		
		Languages language = getLanguages(req).stream()
				.filter(l -> l.getId()
						.intValue() == languageId)
				.findFirst()
				.orElse(null);
		req.setAttribute("selectedLanguage", language);
		
		Map<String, String> languageTranslations =
				serviceFactory.getInstance(ServiceType.LANGUAGES, LanguagesService.class)
						.fetchTranslationsForLanguage(languageId);
		req.setAttribute("languageTranslations", languageTranslations);
		displayPage(req, resp, JSP_PATH + "listTranslations.jsp");
	}

	private void doSaveTranslation(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		String key = getParameterString(req, "key");
		String translation = getParameterString(req, "translation");
		int languageId = getParameterInt(req, "languageId");
		boolean saved = serviceFactory.getInstance(ServiceType.LANGUAGES, LanguagesService.class).insertTranslation(key, translation, languageId);
		
		JsonObject reply = new JsonObject();
		reply.addProperty("success", saved);
		if (saved)
		{
			resp.setStatus(HttpStatus.OK.value());
			reply.addProperty("key", HTMLUtil.validAttribute(key));
			reply.addProperty("translation", translation);
		}
		else
		{
			resp.setStatus(HttpStatus.BAD_REQUEST.value());
		}
		resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
		resp.getWriter().write(reply.toString());
	}

	@Override
	protected NavBarZone getNavBarZone()
	{
		return NavBarZone.ADMIN_LANGUAGES;
	}
}
