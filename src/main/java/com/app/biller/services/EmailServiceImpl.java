package com.app.biller.services;

import static com.app.biller.util.BillerHelper.getUserProfile;

import javax.mail.Message;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.app.biller.dao.UserDao;
import com.app.biller.domain.User;


@Service("emailService")
public class EmailServiceImpl implements EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	UserDao userDao;

	@Override
	public void sendEmail(Object mailObj) {
		//TODO: Need to get mail object with all specific mail details required by preparator
		MimeMessagePreparator preparator = getMessagePreparator(mailObj);
		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			logger.error(ex.getMessage());
		}
	}

	private MimeMessagePreparator getMessagePreparator(Object mailObj) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setFrom("biller@billerinfo.com");
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("adwived8@in.ibm.com"));
				mimeMessage.setText("Approved by " + mailObj);
				mimeMessage.setSubject("Biller Data Approval Notice");
			}
		};
		return preparator;
	}
	
	public String getEmailID(String userID) {
		return userDao.getEmailID(userID);
	}
	
	private MimeMessagePreparator getApprovalMessagePreparator(String approveFor,  String approveBy, String billMonth, String billYear) {
		
		String toEmailID = this.getEmailID(approveFor);
		String pmoEmailID = this.getPmoEmailID();
		String approveByEmailID = this.getEmailID(approveBy);
		User approveByUserProfile = userDao.createUserProfile(approveBy);
		User approveForUserProfile = userDao.createUserProfile(approveFor);
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {	
				mimeMessage.setFrom("biller@billerinfo.com");
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmailID));
				mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(pmoEmailID));
				mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(approveByEmailID));
				mimeMessage.setText("Approved by " + approveBy);
				mimeMessage.setSubject("Biller Notification: Bill Cycle " + billMonth + "-" + billYear + " approved by " + approveByUserProfile.getName() + " for " + approveForUserProfile.getName() );
				
			}
		};
		return preparator;
	}
	
	private MimeMessagePreparator getRejectionMessagePreparator(String rejectedFor, String rejectedBy, String rejectComments, String billMonth, String billYear) {
		
		String rejectedForEmailID = this.getEmailID(rejectedFor);
		String pmoEmailID = this.getPmoEmailID();
		String rejectByEmailID = this.getEmailID(rejectedBy);
		User rejectedByUserProfile = userDao.createUserProfile(rejectedBy);
		User rejectedForUserProfile = userDao.createUserProfile(rejectedFor);
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setFrom("biller@billerinfo.com");
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(rejectedForEmailID));
				mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(pmoEmailID));
				mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(rejectByEmailID));
				mimeMessage.setText("Rejected by " + rejectedBy);
				mimeMessage.setSubject("Biller Notification: Bill Cycle " + billMonth + "-" + billYear + " rejected by " + rejectedByUserProfile.getName() + " for " + rejectedForUserProfile.getName() );
				mimeMessage.setText(rejectComments);
				
			}
		};
		return preparator;
	}
	
	public void sendApprovalEmail(String approveFor,  String approveBy, String billMonth, String billYear) {
		MimeMessagePreparator preparator = getApprovalMessagePreparator(approveFor,approveBy, billMonth, billYear);
		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			logger.error(ex.getMessage());
		}
	}
	
	public String getPmoEmailID() {
		return userDao.getPmoEmailID();
	}
	
	public void sendRejectionEmail(String rejectedFor, String rejectedBy, String rejectComments, String billMonth, String billYear) {
		MimeMessagePreparator preparator = getRejectionMessagePreparator(rejectedFor, rejectedBy, rejectComments, billMonth, billYear);
		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			logger.error(ex.getMessage());
		}
	}
}
