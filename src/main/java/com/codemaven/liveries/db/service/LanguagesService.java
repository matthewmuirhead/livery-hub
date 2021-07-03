package com.codemaven.liveries.db.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.codemaven.generated.tables.pojos.LanguageFields;
import com.codemaven.generated.tables.pojos.LanguageTranslations;
import com.codemaven.generated.tables.pojos.Languages;
import com.codemaven.generated.tables.pojos.Localise;
import com.codemaven.liveries.db.Service;
import com.codemaven.liveries.db.ServiceType;
import com.codemaven.liveries.db.dao.LanguagesDao;
import com.codemaven.liveries.model.ExtendedLocalise;
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

	public boolean createTranslation(String key, List<Languages> languages)
	{
		boolean success = false;
		if (!StringUtil.isNullOrEmpty(key))
		{
			boolean languageFieldExisted = false;
			LanguageFields languageField = dao.fetchLanguageFieldByKey(key);
			if (languageField != null)
			{
				success = true;
				languageFieldExisted = true;
			}
			else
			{
				languageField = new LanguageFields();
				languageField.setFieldKey(key);
				success = dao.saveKey(languageField);
			}

			if (success)
			{
				int fieldId = languageField.getId();
				for (Languages language : languages)
				{
					int languageId = language.getId();
					if (!languageFieldExisted || !dao.translationExists(fieldId, languageId))
					{
						LanguageTranslations languageTranslations = new LanguageTranslations();
						languageTranslations.setLanguageId(languageId);
						languageTranslations.setFieldId(fieldId);
						if (languageId == DEFAULT_LANGUAGE)
						{
							languageTranslations.setTranslation(key);
						}
						else
						{
							languageTranslations.setTranslation("");
						}
						success = dao.saveTranslation(languageTranslations) && success;
					}
				}
			}
		}
		else
		{
			log.debug("Tried creating translation with invalid language id or empty key");
		}
		return success;
	}

	public boolean insertTranslation(String key, String translation, int languageId)
	{
		boolean success = false;
		if (!StringUtil.isNullOrEmpty(key) && !StringUtil.isNullOrEmpty(translation) && languageId > 0)
		{
			LanguageTranslations languageTranslations = new LanguageTranslations();
			languageTranslations.setLanguageId(languageId);
			languageTranslations.setFieldId(dao.fetchFieldIdByKey(key));
			languageTranslations.setTranslation(translation);
			success = dao.saveTranslation(languageTranslations);
		}
		else if (log.isDebugEnabled())
		{
			log.debug("Tried to insert translation with invalid paramenters: {}, {}, {}", key, translation, languageId);
		}
		return success;
	}

	public List<Languages> fetchAllLanguages()
	{
		return dao.fetchAllLanguages();
	}

	public List<ExtendedLocalise> fetchAllLocalise()
	{
		List<Localise> localise = dao.fetchAllLocalise();
		return localise.stream()
				.map(l -> new ExtendedLocalise(l))
				.collect(Collectors.toList());
	}

	@Override
	public ServiceType getType()
	{
		return ServiceType.LANGUAGES;
	}
}
