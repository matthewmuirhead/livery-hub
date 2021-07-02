package com.codemaven.liveries.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class StringUtil
{
	/**
	 * Carriage Return Line Feed
	 */
	public static final String CRLF = "\r\n";

	/**
	 * @deprecated Use StringUtil.isNullOrEmpty instead as this does not only check for null
	 * 
	 * @param input
	 * @returntrue if the string is null or has zero length
	 */
	public static boolean isNull(String input)
	{
		return isNullOrEmpty(input);
	}

	/**
	 * Check whether the supplied string is null or empty
	 * 
	 * @param input
	 * @return true if the string is null or has zero length
	 */
	public static boolean isNullOrEmpty(String input)
	{
		return input == null || input.length() == 0 || input.equals("null");
	}

	/**
	 * Similar Parse(String, String), but uses the default value if the input is empty
	 * 
	 * @param input
	 * @param defaultValue Default Value to use if input is null or empty
	 * @return String input or defaultValue depending on inputs value
	 */
	public static String nullOrEmptyCoalesce(String input, String defaultValue)
	{
		return isNullOrEmpty(input) ? defaultValue : input;
	}

	/**
	 * Checks that both strings are equal, if either are null this will return false
	 * 
	 * @param str1 first String to compare
	 * @param str2 second String to compare
	 * @return true if the strings are equal and not null
	 */
	public static boolean isEqual(String str1, String str2)
	{
		if (isNullOrEmpty(str1) || isNullOrEmpty(str2))
		{
			return false;
		}
		else
		{
			return str1.equals(str2);
		}
	}

	/**
	 * Checks that both strings are equal once set to lower case and whitespace trimmed, if either are null this will return false.
	 * 
	 * @param str1 first String to compare
	 * @param str2 second String to compare
	 * @return true if strings are equal after being set to lowercase and whitespace trimmed, and not null
	 */
	public static boolean isEqualLenient(String str1, String str2)
	{
		if (str1 == null)
		{
			return false;
		}
		if (str2 == null)
		{
			return false;
		}

		str1 = str1.trim().toLowerCase();
		str2 = str2.trim().toLowerCase();

		return str1.equals(str2);
	}

	/**
	 * Parse the object into a string. If it not valid then return an empty string.
	 * 
	 * @param object
	 * @return
	 */
	public static String parse(Object object)
	{
		if (object == null)
		{
			return "";
		}
		try
		{
			String string = (String) object;
			return parse(string);
		}
		catch (ClassCastException e)
		{
			// eat and ignore the exception
			return "";
		}
	}

	/**
	 * Returns the string passed in if it is not null, otherwise returns an empty string ""
	 * 
	 * @param input String to parse
	 * @return input if it is not null, otherwise an empty String
	 */
	public static String parse(String input)
	{
		return parse(input, "");
	}

	/**
	 * Returns the string passed in if it is not null, otherwise it returns the value in strDefault
	 * 
	 * @param input String to parse
	 * @param defaultVal Default value to return if input is null
	 * @return input if it is not null, otherwise defaultVal
	 */
	public static String parse(String input, String defaultVal)
	{
		String value = (input == null) ? defaultVal : input;
		return value;
	}

	/**
	 * Parse a string from the input, returning an empty string if input is null. Allows a maximum length to be specified for the
	 * return String.
	 * 
	 * @param input String
	 * @param maxlength Maximum length of String returned
	 * @return String
	 */
	public static String parse(String input, int maxlength)
	{
		input = parse(input, "").trim();
		input = setMaximumLength(input, maxlength);
		return input;
	}

	/**
	 * If a string is longer than the desired length, substring it (avoids out of bounds exception)
	 * 
	 * Useful for all those DB queries where sanitizing the input hasn't been done.
	 * 
	 * @param input
	 * @param length
	 * @return
	 */
	public static String setMaximumLength(String input, int length)
	{
		if (input.length() > length)
		{
			return input.substring(0, length);
		}
		return input;
	}

	/**
	 * Returns the date object as a string formatted with format
	 * 
	 * Never uses UTC.
	 * 
	 * @param date Date to parse
	 * @param format Format to parse with
	 * @return Date representated a String
	 */
	public static String dateToString(Date date, String format)
	{
		return dateToString(date, format, false);
	}

	/**
	 * Returns the date object as a string formatted with format
	 * 
	 * @param date Date to parse
	 * @param format Format to parse with
	 * @param enableUniversalTimeZone Whether to use UTC
	 * @return Date representated a String
	 */
	public static String dateToString(Date date, String format, boolean enableUniversalTimeZone)
	{
		DateFormat formatter = null;
		String dateString = "";

		if (date == null)
		{
			return "";
		}

		try
		{
			formatter = new SimpleDateFormat(format);
			if (enableUniversalTimeZone)
			{
				formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			}
			dateString = formatter.format(date);
		}
		catch (IllegalArgumentException e)
		{
			dateString = "";
		}

		return dateString;
	}

	public static String replace(String input, String old, String replacement)
	{
		return replace(input, old, replacement, false);
	}

	/**
	 * Replace elements within a new string
	 * 
	 * @param input String to replace elements within
	 * @param old element to replace
	 * @param replacement new element
	 * @return input with old element replaced with replacement element
	 */
	public static String replace(String input, String old, String replacement, boolean replaceOne)
	{
		if (StringUtil.isNullOrEmpty(input))
		{
			return input;
		}
		if (StringUtil.isNullOrEmpty(old))
		{
			return input;
		}
		if (StringUtil.isNullOrEmpty(replacement))
		{
			replacement = "";
		}

		int index = input.indexOf(old);
		while (index != -1)
		{
			input = input.substring(0, index) + replacement + input.substring(index + old.length());
			index += replacement.length();
			index = input.indexOf(old, index);
			if (replaceOne)
			{
				break;
			}
		}
		return input;
	}

	/**
	 * Replace elements for JSON
	 * 
	 * @param input String to replace elements with
	 * @return String with old elements replaced
	 */
	public static String sanitizeForJson(String string)
	{
		if (isNullOrEmpty(string))
		{
			return "";
		}

		char c = 0;
		int i;
		int len = string.length();
		StringBuilder sb = new StringBuilder(len + 4);
		String t;

		// sb.append('"');
		for (i = 0; i < len; i += 1)
		{
			c = string.charAt(i);
			switch (c)
			{
				case '\\':
				case '"':
					sb.append('\\');
					sb.append(c);
					break;
				case '/':
					// if (b == '<') {
					sb.append('\\');
					// }
					sb.append(c);
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\t':
					sb.append("\\t");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\r':
					sb.append("\\r");
					break;
				default:
					if (c < ' ')
					{
						t = "000" + Integer.toHexString(c);
						sb.append("\\u" + t.substring(t.length() - 4));
					}
					else
					{
						sb.append(c);
					}
			}
		}
		// sb.append('"');
		return sb.toString();
	}

	/**
	 * Convert the int value to a String
	 * 
	 * @param value
	 * @return String
	 */
	public static String intToStr(int value)
	{
		return Integer.toString(value);
	}

	/**
	 * Convert a string to a boolean.
	 * 
	 * @param input String to parse
	 * @return true if string matches a positive boolean expression
	 */
	public static boolean strToBoolean(String input)
	{
		if (isNullOrEmpty(input))
		{
			return false;
		}
		input = input.toLowerCase();
		return input.equals("1") || input.equals("true") || input.equals("yes") || input.equals("on");
	}

	/**
	 * Convert a String to a BigDecimal with the fallback to a default value in the event of a parsing error
	 * 
	 * @param input
	 * @param defaultVal
	 * @return
	 */
	public static BigDecimal strToBigDecimal(String input, BigDecimal defaultVal)
	{
		try
		{
			BigDecimal value = new BigDecimal(input);
			return value;
		}
		catch (Exception e)
		{
			return defaultVal;
		}
	}

	/**
	 * Convert a string to an int.
	 * 
	 * If parsing fails then it returns 0
	 * 
	 * @param input String to parse
	 * @return the String value as an int. If it fails to parse then 0 is returned by default
	 */
	public static int strToInt(String input)
	{
		return strToInt(input, 0);
	}

	/**
	 * Convert a string to an int. Use the default value if parsing fails
	 * 
	 * @param input String to parse
	 * @param defaultValue value to return if parsing fails
	 * @return int parsed value of string. default if it fails
	 */
	public static int strToInt(String input, int defaultValue)
	{
		try
		{
			return Integer.parseInt(input);
		}
		catch (Exception e)
		{
			return defaultValue;
		}
	}

	/**
	 * Convert a string to a long. Use the default value if parsing fails.
	 * 
	 * @param input String to parse.
	 * @param defaultValue Value to return if parsing fails.
	 * @return long Parsed value of string, default if it fails.
	 */
	public static long strToLong(String input, long defaultValue)
	{
		try
		{
			return Long.parseLong(input);
		}
		catch (Exception e)
		{
			return defaultValue;
		}
	}

	/**
	 * Parse a String to an Integer, using the default value if parsing fails
	 * 
	 * @param input String to parse
	 * @param defaultValue value to return if parsing fails
	 * @return Integer parsed value of string. default if it fails
	 */
	public static Integer strToInteger(String input, Integer defaultValue)
	{
		try
		{
			return new Integer(input);
		}
		catch (Exception e)
		{
			return defaultValue;
		}
	}

	/**
	 * Convert a string to a float. Use the default value if parsing fails
	 * 
	 * @param input String to parse
	 * @param defaultValue value to return if parsing fails
	 * @return float parsed value of string. default if it fails
	 */
	public static float strToFloat(String input, float defaultValue)
	{
		try
		{
			return Float.parseFloat(input);
		}
		catch (Exception e)
		{
			return defaultValue;
		}
	}

	/**
	 * Convert a String to a float.
	 * 
	 * @param input String to parse
	 * @return float parsed value of string.
	 * @throws NumberFormatException
	 */
	public static float strToFloat(String input) throws NumberFormatException
	{
		return strToFloat(input, 0);
	}

	/**
	 * Convert a String to a double
	 * 
	 * @param input
	 * @return
	 * @throws NumberFormatException
	 */
	public static double strToDouble(String input) throws NumberFormatException
	{
		return strToDouble(input, 0);
	}

	/**
	 * Convert a String to a double, resulting with defaultValue if fails to parse
	 * 
	 * @param input
	 * @param defaultValue
	 * @return
	 */
	public static double strToDouble(String input, double defaultValue)
	{
		try
		{
			return Double.parseDouble(input);
		}
		catch (Exception e)
		{
			return defaultValue;
		}
	}
	
	/**
	 * Convert a Date object to a String in format dd'/'MM'/'yyyy 'at' HH:mm
	 * 
	 * @param date
	 * @return String in format dd'/'MM'/'yyyy 'at' HH:mm
	 */
	public static String fullDateTimeToString(Date date)
	{
		return dateToString(date, "dd'/'MM'/'yyyy 'at' HH:mm");
	}


	/**
	 * Convert a Date object to a String in format d'/'M'/'yyyy 'at' HH:mm
	 * 
	 * @param date
	 * @return String in format d'/'M'/'yyyy 'at' HH:mm
	 */
	public static String fullDateTimeToStringNoLeadingZeros(Date date)
	{
		return dateToString(date, "d'/'M'/'yyyy 'at' HH:mm");
	}

	/**
	 * Convert a Calendar object to a String in format dd'/'MM'/'yyyy 'at' HH:mm
	 * 
	 * @param calendar
	 * @return String in format dd'/'MM'/'yyyy 'at' HH:mm
	 */
	public static String calendarToString(Calendar calendar)
	{
		return fullDateTimeToString(calendar.getTime());
	}

	/**
	 * Find the index of a String in an array
	 * 
	 * @param array Array
	 * @param stringToFind String to find
	 * @return index of stringToFind in array, -1 if not found
	 */
	public static int findIndex(String[] array, String stringToFind)
	{
		if (array != null && stringToFind != null)
		{
			for (int i = 0; i < array.length; i++)
			{
				if (array[i] == null)
				{
					continue;
				}

				if (array[i].equals(stringToFind))
				{
					return i;
				}
			}
		}

		return -1;
	}


	/**
	 * Remove any whitespace from the String provided
	 * 
	 * @param string String
	 * @return string with no whitespace
	 */
	public static String removeWhitespace(String string)
	{
		if (!isNullOrEmpty(string))
		{
			string = string.replaceAll("\\s", "");
		}
		return string;
	}

	/**
	 * Check whether the String array contains the String to find
	 * 
	 * @param array
	 * @param stringToFind
	 * @return true if array contains stringToFind
	 */
	public static boolean arrayContains(String[] array, String stringToFind)
	{
		return findIndex(array, stringToFind) != -1;
	}

	/**
	 * Check if the string contains the 'contains' argument. Allows case insensitivity
	 * 
	 * @param string String to check whether the 'contains' string is within
	 * @param contains
	 * @param ignoreCase true if case is to be ignored
	 * @return true if string contains 'contains'
	 */
	public static boolean stringContains(String string, String contains, boolean ignoreCase)
	{
		boolean contained = false;
		if (string != null)
		{
			if (ignoreCase)
			{
				string = string.toLowerCase();
				contains = contains.toLowerCase();
			}
			if (string.indexOf(contains) > -1)
			{
				contained = true;
			}
		}
		return contained;
	}

	
	/**
	 * Checks that both strings are equal once set to lower case and whitespace trimmed, if either are null this will return false.
	 * 
	 * @param str1 first String to compare
	 * @param str2 second String to compare
	 * @return true if strings are equal after being set to lowercase and whitespace trimmed, and not null
	 */
	/**
	 * Check if a string contains another string when both are lower case.
	 * 
	 * @param string
	 * @param contains
	 * @return true if string contains the other string, false if otherwise.
	 */
	public static boolean containsLenient(String string, String contains)
	{
		if (isNullOrEmpty(string) || isNullOrEmpty(contains))
		{
			return false;
		}
		return string.toLowerCase().contains(contains.toLowerCase());
	}

	/**
	 * Remove numeric characters from a string.
	 * 
	 * @param string the string to remove numeric characters from
	 * @return a string with the nummeric characters removed, will default to an empty string if the string passed in is null or
	 *         empty
	 */
	public static String removeNumericCharacters(String string)
	{
		if (isNullOrEmpty(string))
		{
			return "";
		}
		return string.replaceAll("\\d", "");
	}

	/**
	 * Check if a string contains only numeric characters.
	 * 
	 * @param string the string being checked is numeric
	 * @return true if the string is numeric, will return false if the string is empty.
	 */
	public static boolean isNumeric(String string)
	{
		if (isNullOrEmpty(string))
		{
			return false;
		}
		return string.matches("\\d+");
	}
	
	/**
	 * Checks the input is same and returns string array
	 * 
	 * @param toSplit
	 * @return
	 */
	public static String[] split(String toSplit, String delimiter)
	{
		String[] splitString;
		if (!isNullOrEmpty(toSplit))
		{
			splitString = toSplit.split(delimiter);
		}
		else
		{
			splitString = new String[0];
		}
		return splitString;
	}
}
