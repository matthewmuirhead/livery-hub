package com.codemaven.events.model;

import com.codemaven.generated.tables.pojos.Cars;

public class CarsExtended extends Cars
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getFullName()
	{
		return getManufacturer() + " " + getModel();
	}
	
	public String getFullNameAndYear()
	{
		return getYear() + " " + getManufacturer() + " " + getModel();
	}
}
