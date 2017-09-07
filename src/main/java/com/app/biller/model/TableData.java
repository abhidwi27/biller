package com.app.biller.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TableData {

	public List<String> header;
	public List<?> tableBody;
	
	
	
	public List<String> getHeader() {
		return header;
	}
	public void setHeader(List<String> list) {
		this.header = list;
	}
	
	public List<?> getTableBody() {
		return tableBody;
	}
	public void setTableBody(List<?> tableBody) {
		this.tableBody = tableBody;
	}

}
