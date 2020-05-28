package com.codemaven.events.model;

import java.time.Month;

import com.codemaven.events.manager.lists.EventDetailsList;

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
		String json = "{\"currentMonthDays\":\""+currentMonthDays+"\",";
		json += "\"prevMonthDays\":\""+prevMonthDays+"\",";
		json += "\"nextMonthDays\":\""+nextMonthDays+"\",";
		json += "\"prevMonthStart\":\""+prevMonthStart+"\",";
		json += "\"prevMonthEnd\":\""+prevMonthEnd+"\",";
		json += "\"nextMonthStart\":\""+nextMonthStart+"\",";
		json += "\"nextMonthEnd\":\""+nextMonthEnd+"\",";
		json += "\"currentMonth\":\""+currentMonth+"\",";
		json += "\"currentMonthValue\":\""+currentMonth.getValue()+"\",";
		json += "\"currentYear\":\""+currentYear+"\",";
		json += eventDetails.toCalendarJson()+"}";
		return json;
	}
}
