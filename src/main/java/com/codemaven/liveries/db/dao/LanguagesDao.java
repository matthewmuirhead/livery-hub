package com.codemaven.liveries.db.dao;

import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import com.codemaven.generated.Tables;
import com.codemaven.generated.tables.pojos.LanguageFields;
import com.codemaven.generated.tables.pojos.LanguageTranslations;
import com.codemaven.generated.tables.pojos.Languages;
import com.codemaven.generated.tables.records.LanguageFieldsRecord;
import com.codemaven.generated.tables.records.LanguageTranslationsRecord;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class LanguagesDao
{
	private final DSLContext dsl;

	public Languages fetchLanguageByCode(String code)
	{
		return dsl.selectFrom(Tables.LANGUAGES)
				.where(Tables.LANGUAGES.CODE.eq(code))
				.fetchOneInto(Languages.class);
	}

	public List<Languages> fetchAllLanguages()
	{
		return dsl.selectFrom(Tables.LANGUAGES)
				.fetchInto(Languages.class);
	}

	public Map<String, String> fetchTranslationsForLanguage(int languageId)
	{
		return dsl.select(Tables.LANGUAGE_FIELDS.FIELD_KEY, Tables.LANGUAGE_TRANSLATIONS.TRANSLATION)
				.from(Tables.LANGUAGE_FIELDS.join(Tables.LANGUAGE_TRANSLATIONS)
						.on(Tables.LANGUAGE_FIELDS.ID.eq(Tables.LANGUAGE_TRANSLATIONS.FIELD_ID)))
				.where(Tables.LANGUAGE_TRANSLATIONS.LANGUAGE_ID.eq(languageId))
				.fetchMap(Tables.LANGUAGE_FIELDS.FIELD_KEY, Tables.LANGUAGE_TRANSLATIONS.TRANSLATION);
	}

	public LanguageFields fetchLanguageFieldByKey(String key)
	{
		return dsl.selectFrom(Tables.LANGUAGE_FIELDS)
				.where(Tables.LANGUAGE_FIELDS.FIELD_KEY.eq(key))
				.fetchOneInto(LanguageFields.class);
	}

	public String fetchKeyTranslationForLanguage(String key, int languageId)
	{
		return dsl.select(Tables.LANGUAGE_TRANSLATIONS.TRANSLATION)
				.from(Tables.LANGUAGE_FIELDS.join(Tables.LANGUAGE_TRANSLATIONS)
						.on(Tables.LANGUAGE_FIELDS.ID.eq(Tables.LANGUAGE_TRANSLATIONS.FIELD_ID)))
				.where(Tables.LANGUAGE_TRANSLATIONS.LANGUAGE_ID.eq(languageId))
				.and(Tables.LANGUAGE_FIELDS.FIELD_KEY.eq(key))
				.fetchOneInto(String.class);
	}

	public boolean saveKey(final LanguageFields languageField)
	{
		LanguageFieldsRecord record = dsl.newRecord(Tables.LANGUAGE_FIELDS, languageField);
		Record result = dsl.insertInto(Tables.LANGUAGE_FIELDS)
				.set(record)
				.onDuplicateKeyUpdate()
				.set(record)
				.returningResult(Tables.LANGUAGE_FIELDS.ID)
				.fetchOne();
		languageField.setId(result.getValue(Tables.LANGUAGE_FIELDS.ID));
		return result.getValue(Tables.LANGUAGE_FIELDS.ID) > 0;
	}

	public boolean saveTranslation(final LanguageTranslations languageTranslation)
	{
		LanguageTranslationsRecord record = dsl.newRecord(Tables.LANGUAGE_TRANSLATIONS, languageTranslation);
		return dsl.insertInto(Tables.LANGUAGE_TRANSLATIONS)
				.set(record)
				.onDuplicateKeyUpdate()
				.set(record)
				.execute() > 0;
	}

	public boolean translationExists(int fieldId, int languageId)
	{
		return dsl.fetchExists(dsl.selectFrom(Tables.LANGUAGE_TRANSLATIONS)
				.where(Tables.LANGUAGE_TRANSLATIONS.FIELD_ID.eq(fieldId))
				.and(Tables.LANGUAGE_TRANSLATIONS.LANGUAGE_ID.eq(languageId)));
	}

	public int fetchFieldIdByKey(String key)
	{
		return dsl.select(Tables.LANGUAGE_FIELDS.ID)
				.from(Tables.LANGUAGE_FIELDS)
				.where(Tables.LANGUAGE_FIELDS.FIELD_KEY.eq(key))
				.fetchOneInto(Integer.class);
	}
}
