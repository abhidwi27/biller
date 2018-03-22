package com.app.biller.ui;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.app.biller.domain.User;

@Component
public class ResponseDataEnvelope {

	private TableData tableData;
	private List<String> wrList;
	private List<String> employeeList;
	private List<String> weekEndList;
	private List<User> rejectForUserList;
	private User dataLockedBy;
	private int hasApprovedBillCycle;
	//private String dataLockedBy;

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

	public List<User> getRejectForUserList() {
		return rejectForUserList;
	}

	public void setRejectForUserList(List<User> rejectForUserList) {
		this.rejectForUserList = rejectForUserList;
	}

	public User getDataLockedBy() {
		return dataLockedBy;
	}

	public void setDataLockedBy(User dataLockedBy) {
		this.dataLockedBy = dataLockedBy;
	}

	public int isHasApprovedBillCycle() {
		return hasApprovedBillCycle;
	}

	public void setHasApprovedBillCycle(int hasApprovedBillCycle) {
		this.hasApprovedBillCycle = hasApprovedBillCycle;
	}

	

}
