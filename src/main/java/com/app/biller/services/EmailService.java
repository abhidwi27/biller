package com.app.biller.services;

public interface EmailService {

	void sendEmail(final Object object);
	
	String getEmailID(String userID);
	
	void sendApprovalEmail(String approveFor, String approveBy, String billMonth, String billYear);
	
	String getPmoEmailID();
	
	void sendRejectionEmail(String rejectedFor, String rejectedBy, String rejectComments, String billMonth, String billYear);
}
