package com.codemaven.liveries.db.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.codemaven.generated.tables.pojos.LanguageFields;
import com.codemaven.generated.tables.pojos.LanguageTranslations;
import com.codemaven.generated.tables.pojos.Languages;
import com.codemaven.liveries.db.Service;
import com.codemaven.liveries.db.ServiceType;
import com.codemaven.liveries.db.dao.LanguagesDao;
import com.codemaven.liveries.util.StringUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Component
@Slf4j
public class LanguagesService implements Service
{
	private LanguagesDao dao;
	
	private static final int DEFAULT_LANGUAGE = 1;
	public static final String DEFAULT_LANGUAGE_CODE = "en-gb";
	
	public Languages fetchLanguageByCode(String code)
	{
		Languages language = null;
		if (!StringUtil.isNullOrEmpty(code))
		{
			language = dao.fetchLanguageByCode(code);
		}
		else
		{
			log.debug("Tried to fetch language id with empty code");
		}
		return language;
	}
	
	public Map<String, String> fetchTranslationsForLanguage(int languageId)
	{
		Map<String, String> translations = new HashMap<>();
		if (languageId > 0)
		{
			translations = dao.fetchTranslationsForLanguage(languageId);
		}
		else
		{
			log.debug("Tried loading translations with invalid language id");
		}
		return translations;
	}
	
	public String fetchKeyTranslationForLanguage(String key, int languageId)
	{
		String translation = null;
		if (!StringUtil.isNullOrEmpty(key) && languageId > 0)
		{
			translation = dao.fetchKeyTranslationForLanguage(key, languageId);
		}
		else
		{
			log.debug("Tried loading translation with invalid language id or empty key");
		}
		return translation;
	}
	
	public boolean createTranslation(String key)
	{
		boolean success = false;
		if (!StringUtil.isNullOrEmpty(key))
		{
			LanguageFields languageField = new LanguageFields();
			languageField.setFieldKey(key);
			success = dao.saveKey(languageField);
			if (success)
			{
				LanguageTranslations languageTranslations = new LanguageTranslations();
				languageTranslations.setLanguageId(DEFAULT_LANGUAGE);
				languageTranslations.setFieldId(languageField.getId());
				languageTranslations.setTranslation(key);
				success = dao.saveTranslation(languageTranslations);
			}
			else
			{
				success = false;
			}
		}
		else
		{
			log.debug("Tried creating translation with invalid language id or empty key");
		}
		return success;
	}
	
	@Override
	public ServiceType getType()
	{
		return ServiceType.LANGUAGES;
	}
}
