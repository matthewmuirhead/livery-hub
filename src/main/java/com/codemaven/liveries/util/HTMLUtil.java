package com.codemaven.liveries.util;

public class HTMLUtil
{
	public static String validAttribute(String value)
	{
		if (!StringUtil.isNullOrEmpty(value))
		{
			value = value.replace(" ", "_");
		}
		return value;
	}
}
