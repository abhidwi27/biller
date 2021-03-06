package com.app.biller.ui;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TableData {

	private List<String> tableHeader;
	private List<?> tableBody;

	public List<String> getHeader() {
		return tableHeader;
	}

	public void setHeader(List<String> header) {
		this.tableHeader = header;
	}

	public List<?> getBody() {
		return tableBody;
	}

	public void setBody(List<?> body) {
		this.tableBody = body;
	}
}
