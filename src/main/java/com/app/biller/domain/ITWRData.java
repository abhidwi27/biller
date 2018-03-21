package com.app.biller.domain;

import org.springframework.stereotype.Component;

@Component
public class ITWRData {
	
	private String req_no;
	private String req_title;
	private String coo_intake_no;
	private String it_sme;
	private String bus_area;
	private String work_type;
	private String demand_type;
	private String fund_type;
	private String cost_center;
	private String vendor_class;
	private String asm;
	private String primary_vendor;
	private String asd;
	private String overall_status;
	private String vendor_est_hours;
	private String vendor_actual_hours;
	private String pcr_est_hours;
	private String pcr_actual_hours;
	public String getReq_no() {
		return req_no;
	}
	public void setReq_no(String req_no) {
		this.req_no = req_no;
	}
	public String getReq_title() {
		return req_title;
	}
	public void setReq_title(String req_title) {
		this.req_title = req_title;
	}
	public String getCoo_intake_no() {
		return coo_intake_no;
	}
	public void setCoo_intake_no(String coo_intake_no) {
		this.coo_intake_no = coo_intake_no;
	}
	public String getIt_sme() {
		return it_sme;
	}
	public void setIt_sme(String it_sme) {
		this.it_sme = it_sme;
	}
	public String getBus_area() {
		return bus_area;
	}
	public void setBus_area(String bus_area) {
		this.bus_area = bus_area;
	}
	public String getWork_type() {
		return work_type;
	}
	public void setWork_type(String work_type) {
		this.work_type = work_type;
	}
	public String getDemand_type() {
		return demand_type;
	}
	public void setDemand_type(String demand_type) {
		this.demand_type = demand_type;
	}
	public String getFund_type() {
		return fund_type;
	}
	public void setFund_type(String fund_type) {
		this.fund_type = fund_type;
	}
	public String getCost_center() {
		return cost_center;
	}
	public void setCost_center(String cost_center) {
		this.cost_center = cost_center;
	}
	public String getVendor_class() {
		return vendor_class;
	}
	public void setVendor_class(String vendor_class) {
		this.vendor_class = vendor_class;
	}
	public String getPrimary_vendor() {
		return primary_vendor;
	}
	public String getAsm() {
		return asm;
	}
	public void setAsm(String asm) {
		this.asm = asm;
	}
	public void setPrimary_vendor(String primary_vendor) {
		this.primary_vendor = primary_vendor;
	}
	public String getAsd() {
		return asd;
	}
	public void setAsd(String asd) {
		this.asd = asd;
	}
	public String getOverall_status() {
		return overall_status;
	}
	public void setOverall_status(String overall_status) {
		this.overall_status = overall_status;
	}
	public String getVendor_est_hours() {
		return vendor_est_hours;
	}
	public void setVendor_est_hours(String vendor_est_hours) {
		this.vendor_est_hours = vendor_est_hours;
	}
	public String getVendor_actual_hours() {
		return vendor_actual_hours;
	}
	public void setVendor_actual_hours(String vendor_actual_hours) {
		this.vendor_actual_hours = vendor_actual_hours;
	}
	public String getPcr_est_hours() {
		return pcr_est_hours;
	}
	public void setPcr_est_hours(String pcr_est_hours) {
		this.pcr_est_hours = pcr_est_hours;
	}
	public String getPcr_actual_hours() {
		return pcr_actual_hours;
	}
	public void setPcr_actual_hours(String pcr_actual_hours) {
		this.pcr_actual_hours = pcr_actual_hours;
	}
	


}
