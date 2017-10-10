package com.app.biller.domain;



public class Record {
	
	private String rowID;
	private String[] rowData;
	
	public String getRowID() {
		return rowID;
	}
	public void setRowID(String rowID) {
		this.rowID = rowID;
	}
	public String[] getRowData() {
		return rowData;
	}
	public void setRowData(String[] rowData) {
		this.rowData = rowData;
	}
}
