package com.app.biller.domain;

import org.springframework.stereotype.Component;

@Component
public class Month {

	private String monthID;
	private String monthName;

	public String getMonthID() {
		return monthID;
	}

	public void setMonthID(String monthID) {
		this.monthID = monthID;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
}
