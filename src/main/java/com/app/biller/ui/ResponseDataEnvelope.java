package com.app.biller.ui;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ResponseDataEnvelope {

	private TableData tableData;
	private List<String> wrList;
	private List<String> employeeList;
	private List<String> weekEndList;

	public TableData getTableData() {
		return tableData;
	}

	public void setTableData(TableData tableData) {
		this.tableData = tableData;
	}

	public List<String> getWrList() {
		return wrList;
	}

	public void setWrList(List<String> wrList) {
		this.wrList = wrList;
	}

	public List<String> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<String> employeeList) {
		this.employeeList = employeeList;
	}

	public List<String> getWeekEndList() {
		return weekEndList;
	}

	public void setWeekEndList(List<String> weekEndList) {
		this.weekEndList = weekEndList;
	}

}
