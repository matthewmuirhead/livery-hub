package com.codemaven.manager.model;

import java.time.Month;

import com.codemaven.manager.lists.EventDetailsList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CalendarUpdateReplyJson
{
	// Days in Month to show
	private int currentMonthDays = 0;
	private int prevMonthDays = 0;
	private int nextMonthDays = 0;
	// Day of Month
	private int prevMonthStart = 0;
	private int prevMonthEnd = 0;
	private int nextMonthStart = 0;
	private int nextMonthEnd = 0;
	// Event Details
	private EventDetailsList eventDetails;
	// Current Month Details
	Month currentMonth;
	int currentYear = 2020;
	
	public String toJsonString()
	{
		String returnString = "{\"currentMonthDays\":\""+currentMonthDays+"\",";
		returnString += "\"prevMonthDays\":\""+prevMonthDays+"\",";
		returnString += "\"nextMonthDays\":\""+nextMonthDays+"\",";
		returnString += "\"prevMonthStart\":\""+prevMonthStart+"\",";
		returnString += "\"prevMonthEnd\":\""+prevMonthEnd+"\",";
		returnString += "\"nextMonthStart\":\""+nextMonthStart+"\",";
		returnString += "\"nextMonthEnd\":\""+nextMonthEnd+"\",";
		returnString += "\"currentMonth\":\""+currentMonth+"\",";
		returnString += "\"currentMonthValue\":\""+currentMonth.getValue()+"\",";
		returnString += "\"currentYear\":\""+currentYear+"\"}";
		return returnString;
	}
}
