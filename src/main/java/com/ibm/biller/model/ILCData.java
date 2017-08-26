package com.ibm.biller.model;

import org.springframework.stereotype.Component;

@Component
public class ILCData {

	private int seqID;	
	private String EmpID;
	private String EmpName;
	private String ClaimCode;
	private String Activity;
	private String WeekEndDate;
	private int TotHrs;
	private String ShiftType;
	private String UsInd;
	private String OnOffShore;
	private String BillingType;
	private String Category;
	private String Bam;
	private String AppArea;
	private String BusinessArea;
	private String Month;
	private String Quarter;
	private String Dm;
	private String Asm;
	private String Asd;
	private String WrNo;
	private String IsTicket;
	private String StaffType;
	private String IsCTC;
	private String IsRTC;
	private int PlannedHrs;
	private String IsBillable;
	private String Remarks;
	private String CtcOrRtc;
	private String WorkType;
	private String WrDesc;
	private String CostCenter;
	private String Category2;
	private String OnOffLanded;
	private String Tower;
	private String AsmItwr;
	private String AsdItwr;
	private int ItwrActual;
	private String GroupType;
	private String VendorClass;
	private String WrIncDef;
	private String billCycle;

	public int getSeqID() {
		return seqID;
	}
	public void setSeqID(int seqID) {
		this.seqID = seqID;
	}
	public String getEmpID() {
		return EmpID;
	}
	public void setEmpID(String empID) {
		EmpID = empID;
	}
	public String getEmpName() {
		return EmpName;
	}
	public void setEmpName(String empName) {
		EmpName = empName;
	}
	public String getClaimCode() {
		return ClaimCode;
	}
	public void setClaimCode(String claimCode) {
		ClaimCode = claimCode;
	}
	public String getActivity() {
		return Activity;
	}
	public void setActivity(String activity) {
		Activity = activity;
	}
	public String getWeekEndDate() {
		return WeekEndDate;
	}
	public void setWeekEndDate(String weekEndDate) {
		WeekEndDate = weekEndDate;
	}
	public int getTotHrs() {
		return TotHrs;
	}
	public void setTotHrs(int totHrs) {
		TotHrs = totHrs;
	}
	public String getShiftType() {
		return ShiftType;
	}
	public void setShiftType(String shiftType) {
		ShiftType = shiftType;
	}
	public String getUsInd() {
		return UsInd;
	}
	public void setUsInd(String usInd) {
		UsInd = usInd;
	}
	public String getOnOffShore() {
		return OnOffShore;
	}
	public void setOnOffShore(String onOffShore) {
		OnOffShore = onOffShore;
	}
	public String getBillingType() {
		return BillingType;
	}
	public void setBillingType(String billingType) {
		BillingType = billingType;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public String getBam() {
		return Bam;
	}
	public void setBam(String bam) {
		Bam = bam;
	}
	public String getAppArea() {
		return AppArea;
	}
	public void setAppArea(String appArea) {
		AppArea = appArea;
	}
	public String getBusinessArea() {
		return BusinessArea;
	}
	public void setBusinessArea(String businessArea) {
		BusinessArea = businessArea;
	}
	public String getMonth() {
		return Month;
	}
	public void setMonth(String month) {
		Month = month;
	}
	public String getQuarter() {
		return Quarter;
	}
	public void setQuarter(String quarter) {
		Quarter = quarter;
	}
	public String getDm() {
		return Dm;
	}
	public void setDm(String dm) {
		Dm = dm;
	}
	public String getAsm() {
		return Asm;
	}
	public void setAsm(String asm) {
		Asm = asm;
	}
	public String getAsd() {
		return Asd;
	}
	public void setAsd(String asd) {
		Asd = asd;
	}
	public String getWrNo() {
		return WrNo;
	}
	public void setWrNo(String wrNo) {
		WrNo = wrNo;
	}
	public String getIsTicket() {
		return IsTicket;
	}
	public void setIsTicket(String isTicket) {
		IsTicket = isTicket;
	}
	public String getStaffType() {
		return StaffType;
	}
	public void setStaffType(String staffType) {
		StaffType = staffType;
	}
	public String getIsCTC() {
		return IsCTC;
	}
	public void setIsCTC(String isCTC) {
		IsCTC = isCTC;
	}
	public String getIsRTC() {
		return IsRTC;
	}
	public void setIsRTC(String isRTC) {
		IsRTC = isRTC;
	}
	public int getPlannedHrs() {
		return PlannedHrs;
	}
	public void setPlannedHrs(int plannedHrs) {
		PlannedHrs = plannedHrs;
	}
	public String getIsBillable() {
		return IsBillable;
	}
	public void setIsBillable(String isBillable) {
		IsBillable = isBillable;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public String getCtcOrRtc() {
		return CtcOrRtc;
	}
	public void setCtcOrRtc(String ctcOrRtc) {
		CtcOrRtc = ctcOrRtc;
	}
	public String getWorkType() {
		return WorkType;
	}
	public void setWorkType(String workType) {
		WorkType = workType;
	}
	public String getWrDesc() {
		return WrDesc;
	}
	public void setWrDesc(String wrDesc) {
		WrDesc = wrDesc;
	}
	public String getCostCenter() {
		return CostCenter;
	}
	public void setCostCenter(String costCenter) {
		CostCenter = costCenter;
	}
	public String getCategory2() {
		return Category2;
	}
	public void setCategory2(String category2) {
		Category2 = category2;
	}
	public String getOnOffLanded() {
		return OnOffLanded;
	}
	public void setOnOffLanded(String onOffLanded) {
		OnOffLanded = onOffLanded;
	}
	public String getTower() {
		return Tower;
	}
	public void setTower(String tower) {
		Tower = tower;
	}
	public String getAsmItwr() {
		return AsmItwr;
	}
	public void setAsmItwr(String asmItwr) {
		AsmItwr = asmItwr;
	}
	public String getAsdItwr() {
		return AsdItwr;
	}
	public void setAsdItwr(String asdItwr) {
		AsdItwr = asdItwr;
	}
	public int getItwrActual() {
		return ItwrActual;
	}
	public void setItwrActual(int itwrActual) {
		ItwrActual = itwrActual;
	}
	public String getGroupType() {
		return GroupType;
	}
	public void setGroupType(String groupType) {
		GroupType = groupType;
	}
	public String getVendorClass() {
		return VendorClass;
	}
	public void setVendorClass(String vendorClass) {
		VendorClass = vendorClass;
	}
	public String getWrIncDef() {
		return WrIncDef;
	}
	public void setWrIncDef(String wrIncDef) {
		WrIncDef = wrIncDef;
	}
	public String getBillCycle() {
		return billCycle;
	}
	public void setBillCycle(String billCycle) {
		this.billCycle = billCycle;
	}

}
