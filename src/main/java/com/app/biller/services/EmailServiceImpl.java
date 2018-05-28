package com.app.biller.services;

import static com.app.biller.util.BillerHelper.getUserEmailId;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.app.biller.dao.UserDao;
import com.app.biller.domain.User;
import com.app.biller.util.BillerUtil;


@Service("emailService")
public class EmailServiceImpl implements EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	UserDao userDao;

	@Value("${leads.group.mailid}")
	private String leadsMailId;

	@Value("${dm.group.mailid}")
	private String dmMailId;

	@Value("${bam.group.mailid}")
	private String bamMailId;

	@Value("${pmo.group.mailid}")
	private String pmoMailId;

	@Override
	public void sendEmail(Object mailObj) {
		MimeMessagePreparator preparator = getMessagePreparator(mailObj);
		try {
			mailSender.send(preparator);
			logger.info("Email sent with details: " + preparator.toString());
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

	private MimeMessagePreparator getApprovalMessagePreparator(String approveFor,  String approveBy, String billMonth, String billYear) {
		String toEmailID = getUserEmailId(approveFor);
		String pmoEmailID = this.getPmoEmailID();
		String approveByEmailID = getUserEmailId(approveBy);
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
		String rejectedForEmailID = getUserEmailId(rejectedFor);
		String pmoEmailID = this.getPmoEmailID();
		String rejectByEmailID = getUserEmailId(rejectedBy);
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

	@SuppressWarnings("unchecked")
	private MimeMessagePreparator getFileUploadMessagePreparator(String dataType, String month, String year, String weekEnd) {
		
		String [] leadsEmailIdlistArr = new String[0];
		String [] dmEmailIdlistArr = new String[0];
		String [] bamEmailIdlistArr = new String[0];
		String [] srBamEmailIdlistArr = new String[0];
		String [] accountManagerEmailIdlistArr = new String[0];
		String [] pmoEmailIdlistArr = new String[0];
		
	
		
		List<String> leadsEmailIdlist = userDao.getEmailListByRole(1);
		if(leadsEmailIdlist != null) {
			leadsEmailIdlistArr = userDao.getEmailListByRole(1).toArray(new String [leadsEmailIdlist.size()]);
		}
		
		List<String> dmEmailIdlist = userDao.getEmailListByRole(2);
		if(dmEmailIdlist != null) {
			dmEmailIdlistArr = userDao.getEmailListByRole(2).toArray(new String [dmEmailIdlist.size()]);
		}
		
		List<String> bamEmailIdlist = userDao.getEmailListByRole(3);
		if(bamEmailIdlist != null) {
			bamEmailIdlistArr = userDao.getEmailListByRole(3).toArray(new String [bamEmailIdlist.size()]);
		}
		List<String> srBamEmailIdlist = userDao.getEmailListByRole(4);
		if(srBamEmailIdlist != null) {
			srBamEmailIdlistArr = userDao.getEmailListByRole(4).toArray(new String [srBamEmailIdlist.size()]);
		}
		List<String> accountManagerEmailIdlist = userDao.getEmailListByRole(5);
		if( accountManagerEmailIdlist != null) {
			accountManagerEmailIdlistArr = userDao.getEmailListByRole(5).toArray(new String [accountManagerEmailIdlist.size()]);
		}
		List<String> pmoEmailIdlist = userDao.getEmailListByRole(8);		
		if(pmoEmailIdlist != null) {
			pmoEmailIdlistArr = userDao.getEmailListByRole(8).toArray(new String [pmoEmailIdlist.size()]);
		}
		
		
		InternetAddress[] recipientAddressLeads = new InternetAddress[leadsEmailIdlistArr.length];
		int leadCounter = 0;
		try {
		for (String recipient : leadsEmailIdlistArr) {			
			recipientAddressLeads[leadCounter] = new InternetAddress(recipient.trim());
			leadCounter++;
		}
		}catch(AddressException ae) {
			logger.error("Error while creating internet address", ae);
		}
		
		InternetAddress[] recipientAddressDms = new InternetAddress[dmEmailIdlistArr.length];
		int dmCounter = 0;
		try {
		for (String recipient : dmEmailIdlistArr) {			
			recipientAddressDms[dmCounter] = new InternetAddress(recipient.trim());
			dmCounter++;
		}
		}catch(AddressException ae) {
			logger.error("Error while creating internet address", ae);
		}
		
		InternetAddress[] recipientAddressBams = new InternetAddress[bamEmailIdlistArr.length];
		int bamCounter = 0;
		try {
		for (String recipient : bamEmailIdlistArr) {			
			recipientAddressBams[bamCounter] = new InternetAddress(recipient.trim());
			bamCounter++;
		}
		}catch(AddressException ae) {
			logger.error("Error while creating internet address", ae);
		}
		
		InternetAddress[] recipientAddressSrBam = new InternetAddress[srBamEmailIdlistArr.length];
		int srBamCounter = 0;
		try {
		for (String recipient : srBamEmailIdlistArr) {			
			recipientAddressSrBam[srBamCounter] = new InternetAddress(recipient.trim());
			srBamCounter++;
		}
		}catch(AddressException ae) {
			logger.error("Error while creating internet address", ae);
		}
		
		InternetAddress[] recipientAddressAccountManager = new InternetAddress[accountManagerEmailIdlistArr.length];
		int accountManagerCounter = 0;
		try {
		for (String recipient : accountManagerEmailIdlistArr) {			
			recipientAddressAccountManager[accountManagerCounter] = new InternetAddress(recipient.trim());
			accountManagerCounter++;
		}
		}catch(AddressException ae) {
			logger.error("Error while creating internet address", ae);
		}
		
		InternetAddress[] recipientAddressPmo = new InternetAddress[pmoEmailIdlistArr.length];
		int pmoCounter = 0;
		try {
		for (String recipient : pmoEmailIdlistArr) {			
			recipientAddressPmo[pmoCounter] = new InternetAddress(recipient.trim());
			pmoCounter++;
		}
		}catch(AddressException ae) {
			logger.error("Error while creating internet address", ae);
		}
	
		InternetAddress[] recipientAddressSLACc = BillerUtil.joinArrayGeneric(recipientAddressBams, recipientAddressSrBam, recipientAddressAccountManager, recipientAddressPmo);
		if(dataType.equals("ILC")) {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					mimeMessage.setFrom("biller@biller-app.com");
					mimeMessage.setRecipients(Message.RecipientType.TO, recipientAddressLeads);
					mimeMessage.setRecipients(Message.RecipientType.CC, recipientAddressPmo);
					mimeMessage.setText("New "+ dataType + " Data is uploaded for Bill Cycle: "+ month + "-" + year);
					mimeMessage.setSubject("Biller Notification: New " + dataType + " Data for " + month + " - " + year + " Uploaded.");
				}
			};
			return preparator;
		}else {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					mimeMessage.setFrom("biller@biller-app.com");
					mimeMessage.setRecipients(Message.RecipientType.TO, recipientAddressDms);
					mimeMessage.setRecipients(Message.RecipientType.CC, recipientAddressSLACc);
					//mimeMessage.setRecipients(Message.RecipientType.CC, recipientAddressAccountManager);
					//mimeMessage.addRecipients(Message.RecipientType.CC, recipientAddressPmo);
					mimeMessage.setText("New "+ dataType + " Data is uploaded for Bill Cycle: "+ month + "-" + year);
					mimeMessage.setSubject("Biller Notification: New " + dataType + " Data for " + month + " - " + year + " Uploaded.");
				}
			};
			return preparator;
		}
		
	}

	private MimeMessagePreparator getDelegationMessagePreparator(String delegatedBy, String delegatedTo, String delegationStatus) {
		String delegatedByEmailID = getUserEmailId(delegatedBy);
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setFrom("biller@biller-app.com");
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(delegatedByEmailID));
				mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(pmoMailId));
				if(delegationStatus.equals("SET")) {
					String delegatedToEmailID = getUserEmailId(delegatedTo);
					mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(delegatedToEmailID));
					mimeMessage.setText("Delegation " + delegationStatus + " By " + delegatedByEmailID + " To " + delegatedToEmailID);
				} else if(delegationStatus.equals("UNSET")) {
					mimeMessage.setText("Delegation " + delegationStatus + " By " + delegatedByEmailID);
				}
				mimeMessage.setSubject("Biller Notification: Delegation " + delegationStatus + " By " + delegatedByEmailID);	
			}
		};
		return preparator;
	}

	@Async("threadPoolTaskExecutor")
	public void sendRejectionEmail(String rejectedFor, String rejectedBy, String rejectComments, String billMonth, String billYear) {
		MimeMessagePreparator preparator = getRejectionMessagePreparator(rejectedFor, rejectedBy, rejectComments, billMonth, billYear);
		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			logger.error(ex.getMessage());
		}
	}

	@Async("threadPoolTaskExecutor")
	public void sendApprovalEmail(String approveFor,  String approveBy, String billMonth, String billYear) {
		MimeMessagePreparator preparator = getApprovalMessagePreparator(approveFor,approveBy, billMonth, billYear);
		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			logger.error(ex.getMessage());
		}
	}

	@Async("threadPoolTaskExecutor")
	public void sendFileUploadEmail(String dataType, String month, String year, String weekEnd) {
		MimeMessagePreparator preparator = getFileUploadMessagePreparator(dataType, month, year, weekEnd);
		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			logger.error(ex.getMessage());
		}
	}

	@Async("threadPoolTaskExecutor")
	public void sendDelegationEmail(String delegatedBy, String delegatedTo, String delegationStatus) {
		MimeMessagePreparator preparator = getDelegationMessagePreparator(delegatedBy, delegatedTo, delegationStatus);
		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			logger.error(ex.getMessage());
		}
	}

	private String getPmoEmailID() {
		return userDao.getPmoEmailID();
	}
	
	
}
