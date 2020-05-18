package com.codemaven.manager.model;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AjaxSaveReplyJson
{
	public static final String SUCCESS_KEY = "successMessage";
	public static final String GENERAL_ERROR = "generalError";
	
	Map<String, String> errorMessages = new HashMap<>();
	Map<String, String> newHtmlValues = new HashMap<>();
	
	public void addErrorMessage(final String key, final String value)
	{
		errorMessages.put(key, value);
	}
	
	public void addNewHtmlValue(final String key, final String value)
	{
		newHtmlValues.put(key, value);
	}
	
	public String toJsonString()
	{
		String delimiter = "";
		String returnString = "{\"ErrorMessages\":{";
		
		for (Map.Entry<String, String> entry : errorMessages.entrySet()) {
			returnString += delimiter;
			returnString += "\""+entry.getKey()+"\"";
	        returnString += ":";
	        returnString += "\""+entry.getValue()+"\"";
	        delimiter = ",";
	    }
		delimiter = "";
		returnString += "}, \"NewHtmlElementValues\":{";
		for (Map.Entry<String, String> entry : newHtmlValues.entrySet()) {
			returnString += delimiter;
			returnString += "\""+entry.getKey()+"\"";
	        returnString += ":";
	        returnString += "\""+entry.getValue()+"\"";
	        delimiter = ",";
	    }
		returnString += "}}";
		return returnString;
	}
}
