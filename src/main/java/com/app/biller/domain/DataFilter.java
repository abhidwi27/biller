package com.app.biller.domain;

import org.springframework.stereotype.Component;

@Component
public class DataFilter {

	private int dataType;
	private String billCycle;
	private int towerID;
	private String wrNo;
	private String weekEndDate;
	private String empName;
	private int billable;
	private String remarks;
	private int accountId;

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public String getBillCycle() {
		return billCycle;
	}

	public void setBillCycle(String billCycle) {
		this.billCycle = billCycle;
	}

	public int getTowerID() {
		return towerID;
	}

	public void setTowerID(int towerID) {
		this.towerID = towerID;
	}

	public String getWrNo() {
		return wrNo;
	}

	public void setWrNo(String wrNo) {
		this.wrNo = wrNo;
	}

	public String getWeekEndDate() {
		return weekEndDate;
	}

	public void setWeekEndDate(String weekEndDate) {
		this.weekEndDate = weekEndDate;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getBillable() {
		return billable;
	}

	public void setBillable(int billable) {
		this.billable = billable;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

}
