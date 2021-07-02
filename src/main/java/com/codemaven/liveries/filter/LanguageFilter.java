package com.codemaven.liveries.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.codemaven.generated.tables.pojos.Languages;
import com.codemaven.liveries.db.service.LanguagesService;
import com.codemaven.liveries.manager.enums.NavBarZone;
import com.codemaven.liveries.manager.lists.LanguageFieldsList;
import com.codemaven.liveries.servlet.ServletBase;
import com.codemaven.liveries.util.StringUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
@Order(2)
public class LanguageFilter extends ServletBase implements Filter
{
	private LanguagesService languageService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletRequest req = (HttpServletRequest) request;
		String newLanguageCode = getParameterString(req, "lang");
		Languages currentLanguage = getCurrentLanguage(req);
		if (currentLanguage != null && !StringUtil.isEqual(currentLanguage.getCode(), newLanguageCode))
		{
			currentLanguage = languageService.fetchLanguageByCode(newLanguageCode);
			if (currentLanguage != null)
			{
				setSessionValue(req, LANGUAGE_SESSION_KEY, currentLanguage);
			}
			else
			{
				currentLanguage = getCurrentLanguage(req);
			}
		}
		else if (currentLanguage == null)
		{
			if (!StringUtil.isNullOrEmpty(newLanguageCode))
			{
				currentLanguage = languageService.fetchLanguageByCode(newLanguageCode);
				setSessionValue(req, LANGUAGE_SESSION_KEY, currentLanguage);
			}
			else
			{
				currentLanguage = languageService.fetchLanguageByCode(LanguagesService.DEFAULT_LANGUAGE_CODE);
				setSessionValue(req, LANGUAGE_SESSION_KEY, currentLanguage);
			}
		}

		LanguageFieldsList languageFieldsList = new LanguageFieldsList(languageService);
		languageFieldsList.populateTranslations(currentLanguage.getId());
		req.setAttribute("languageFieldsList", languageFieldsList);
		chain.doFilter(req, resp);
	}

	@Override
	protected NavBarZone getNavBarZone()
	{
		return null;
	}
}