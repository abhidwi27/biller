package com.app.biller.services;

public interface EmailService {
	
//	String getEmailID(String userID);
	
	void sendApprovalEmail(String approveFor, String approveBy, String billMonth, String billYear);
	
//	String getPmoEmailID();
	
	void sendRejectionEmail(String rejectedFor, String rejectedBy, String rejectComments, String billMonth, String billYear);

	void sendFileUploadEmail(String dataType, String month, String year, String weekEnd);

	void sendDelegationEmail(String delegatedBy, String delegatedTo, String delegationStatus);
}
