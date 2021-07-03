package com.codemaven.liveries.manager.lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.codemaven.generated.tables.pojos.Languages;
import com.codemaven.liveries.db.service.LanguagesService;
import com.codemaven.liveries.util.StringUtil;

public class LanguageFieldsList
{
	private Map<String, String> translations = new HashMap<>();
	private int languageId;
	private Pattern placeholderPattern = Pattern.compile("\\{(.*?)}");
	private LanguagesService service;
	private List<Languages> languages;

	public LanguageFieldsList(LanguagesService service, List<Languages> languages)
	{
		this.service = service;
		this.languages = languages;
	}

	public void populateTranslations(int languageId)
	{
		this.languageId = languageId;
		translations.putAll(service.fetchTranslationsForLanguage(languageId));
	}

	public String getTranslation(String key)
	{
		return getTranslation(key, new String[] {});
	}

	public String getTranslation(String key, String replacement)
	{
		return getTranslation(key, new String[] { replacement });
	}

	public String getTranslation(String key, String[] replacements)
	{
		if (StringUtil.isNullOrEmpty(key))
		{
			return "";
		}

		if (replacements.length > 0)
		{
			List<String> placeholders = new ArrayList<>();
			Matcher matcher = placeholderPattern.matcher(key);
			while (matcher.find())
			{
				placeholders.add(matcher.group(1));
			}

			return getTranslation(key, placeholders.toArray(new String[placeholders.size()]), replacements);
		}
		else
		{
			return getTranslation(key, replacements, replacements);
		}
	}

	/**
	 * 
	 * @param key
	 * @param placeHolders
	 * @param values
	 * @return
	 */
	public String getTranslation(String key, String[] placeHolders, String[] values)
	{
		if (StringUtil.isNullOrEmpty(key))
		{
			return "";
		}

		String translation = translations.get(key);
		if (translation == null)
		{
			translation = service.fetchKeyTranslationForLanguage(key, languageId);
			if (translation == null)
			{
				service.createTranslation(key, languages);
				translations.put(key, key);
				translation = key;
			}
		}

		if (StringUtil.isNullOrEmpty(translation))
		{
			translation = key;
		}

		for (int i = 0; i < placeHolders.length && i < values.length; i++)
		{
			translation = StringUtil.replace(translation, "{" + placeHolders[i] + "}", values[i]);
		}

		return translation;
	}
}