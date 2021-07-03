package com.codemaven.liveries.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.codemaven.generated.tables.pojos.Localise;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExtendedLocalise extends Localise
{
	private static final long serialVersionUID = 1L;

	DateTimeFormatter dateFormatter;

	public ExtendedLocalise(Localise localise)
	{
		super(localise);
		dateFormatter = DateTimeFormatter.ofPattern(localise.getDateFormat());
	}

	public String formatDate(LocalDate date)
	{
		return date.format(dateFormatter);
	}
}
