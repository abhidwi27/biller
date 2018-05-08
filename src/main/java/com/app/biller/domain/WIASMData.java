package com.app.biller.domain;

import org.springframework.stereotype.Component;

@Component
public class WIASMData {
	
	private String acc_id;
	private String wrkItem_id;
	private String wrkItem_desc;
	private String category;
	private String on_off_shore;
	private String billing_type;
	private String application;
	private String buss_area;
	private String bam;
	public String getAcc_id() {
		return acc_id;
	}
	public void setAcc_id(String acc_id) {
		this.acc_id = acc_id;
	}
	public String getWrkItem_id() {
		return wrkItem_id;
	}
	public void setWrkItem_id(String wrkItem_id) {
		this.wrkItem_id = wrkItem_id;
	}
	public String getWrkItem_desc() {
		return wrkItem_desc;
	}
	public void setWrkItem_desc(String wrkItem_desc) {
		this.wrkItem_desc = wrkItem_desc;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getOn_off_shore() {
		return on_off_shore;
	}
	public void setOn_off_shore(String on_off_shore) {
		this.on_off_shore = on_off_shore;
	}
	public String getBilling_type() {
		return billing_type;
	}
	public void setBilling_type(String billing_type) {
		this.billing_type = billing_type;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getBuss_area() {
		return buss_area;
	}
	public void setBuss_area(String buss_area) {
		this.buss_area = buss_area;
	}
	public String getBam() {
		return bam;
	}
	public void setBam(String bam) {
		this.bam = bam;
	}
	public String getDm() {
		return dm;
	}
	public void setDm(String dm) {
		this.dm = dm;
	}
	public String getAsm() {
		return asm;
	}
	public void setAsm(String asm) {
		this.asm = asm;
	}
	public String getAsd() {
		return asd;
	}
	public void setAsd(String asd) {
		this.asd = asd;
	}
	private String dm;
	private String asm;
	private String asd;
	
	


}
