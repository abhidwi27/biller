package com.app.biller.services;


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
import com.app.biller.util.BillerHelper;
import com.app.biller.util.BillerUtil;


@Service("emailService")
public class EmailServiceImpl implements EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	UserDao userDao;
	
	@Autowired
	BillerHelper billerHelper;

	@Value("${leads.group.mailid}")
	private String leadsMailId;

	@Value("${dm.group.mailid}")
	private String dmMailId;

	@Value("${bam.group.mailid}")
	private String bamMailId;

	@Value("${pmo.group.mailid}")
	private String pmoMailId;

	private MimeMessagePreparator getApprovalMessagePreparator(String approveFor,  String approveBy, String billMonth, String billYear) {
		String toEmailID = billerHelper.getUserEmailId(approveFor);
		String pmoEmailID = this.getPmoEmailID();
		String approveByEmailID = billerHelper.getUserEmailId(approveBy);
		User approveByUserProfile = userDao.createUserProfile(approveBy);
		User approveForUserProfile = userDao.createUserProfile(approveFor);
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {	
				mimeMessage.setFrom("Biller@biller-app.com");
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
		String rejectedForEmailID = billerHelper.getUserEmailId(rejectedFor);
		String pmoEmailID = this.getPmoEmailID();
		String rejectByEmailID = billerHelper.getUserEmailId(rejectedBy);
		User rejectedByUserProfile = userDao.createUserProfile(rejectedBy);
		User rejectedForUserProfile = userDao.createUserProfile(rejectedFor);
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setFrom("Biller@biller-app.com");
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

	private MimeMessagePreparator getFileUploadMessagePreparator(String dataType, String month, String year, String weekEnd) {
		
		String [] leadsEmailIdlistArr = new String[0];
		String [] dmEmailIdlistArr = new String[0];
		String [] bamEmailIdlistArr = new String[0];
		String [] srBamEmailIdlistArr = new String[0];
		String [] accountManagerEmailIdlistArr = new String[0];
		String [] dpeEmailIdlistArr = new String[0];
		String [] accountExecutiveManagerEmailIdlistArr = new String[0];
		String [] pmoEmailIdlistArr = new String[0];
		
	
		
		List<String> leadsEmailIdlist = userDao.getEmailListByRole(1);
		if(leadsEmailIdlist != null) {
			leadsEmailIdlistArr = leadsEmailIdlist.toArray(new String [leadsEmailIdlist.size()]);
		}
		
		List<String> dmEmailIdlist = userDao.getEmailListByRole(2);
		if(dmEmailIdlist != null) {
			dmEmailIdlistArr = dmEmailIdlist.toArray(new String [dmEmailIdlist.size()]);
		}
		
		List<String> bamEmailIdlist = userDao.getEmailListByRole(3);
		if(bamEmailIdlist != null) {
			bamEmailIdlistArr = bamEmailIdlist.toArray(new String [bamEmailIdlist.size()]);
		}
		List<String> srBamEmailIdlist = userDao.getEmailListByRole(4);
		if(srBamEmailIdlist != null) {
			srBamEmailIdlistArr = srBamEmailIdlist.toArray(new String [srBamEmailIdlist.size()]);
		}
		List<String> accountManagerEmailIdlist = userDao.getEmailListByRole(5);
		if( accountManagerEmailIdlist != null) {
			accountManagerEmailIdlistArr = accountManagerEmailIdlist.toArray(new String [accountManagerEmailIdlist.size()]);
		}		
		List<String> dpeEmailIdlist = userDao.getEmailListByRole(6);
		if( dpeEmailIdlist != null) {
			dpeEmailIdlistArr = dpeEmailIdlist.toArray(new String [accountManagerEmailIdlist.size()]);
		}
		List<String> accountExecutiveEmailIdlist = userDao.getEmailListByRole(7);
		if( accountExecutiveEmailIdlist != null) {
			accountExecutiveManagerEmailIdlistArr = accountExecutiveEmailIdlist.toArray(new String [accountManagerEmailIdlist.size()]);
		}
		List<String> pmoEmailIdlist = userDao.getEmailListByRole(8);		
		if(pmoEmailIdlist != null) {
			pmoEmailIdlistArr = pmoEmailIdlist.toArray(new String [pmoEmailIdlist.size()]);
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
		InternetAddress[] recipientAddressDpe = new InternetAddress[dpeEmailIdlistArr.length];
		int dpeCounter = 0;
		try {
		for (String recipient : dpeEmailIdlistArr) {			
			recipientAddressDpe[dpeCounter] = new InternetAddress(recipient.trim());
			dpeCounter++;
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
		InternetAddress[] recipientAddressAccountExecutive = new InternetAddress[accountExecutiveManagerEmailIdlistArr.length];
		int accountExecutiveCounter = 0;
		try {
		for (String recipient : accountExecutiveManagerEmailIdlistArr) {			
			recipientAddressAccountExecutive[accountExecutiveCounter] = new InternetAddress(recipient.trim());
			accountExecutiveCounter++;
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
		InternetAddress[] recipientAddressIlcCc = BillerUtil.joinArrayGeneric(recipientAddressDms, recipientAddressBams, recipientAddressSrBam, recipientAddressDpe, recipientAddressAccountManager, recipientAddressAccountExecutive, recipientAddressPmo);
		InternetAddress[] recipientAddressSlaCc = BillerUtil.joinArrayGeneric(recipientAddressLeads, recipientAddressBams, recipientAddressSrBam, recipientAddressDpe, recipientAddressAccountManager, recipientAddressAccountExecutive, recipientAddressPmo);
		if(dataType.equals("ILC")) {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					mimeMessage.setFrom("Biller@biller-app.com");
					mimeMessage.setRecipients(Message.RecipientType.TO, recipientAddressLeads);
					mimeMessage.setRecipients(Message.RecipientType.CC, recipientAddressIlcCc);
					mimeMessage.setText("New "+ dataType + " Data is uploaded for WE: "+ weekEnd);
					mimeMessage.setSubject("Biller Notification: New " + dataType + " Data for WE: " + weekEnd  + " Uploaded.");
				}
			};
			return preparator;
		}else {		
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					mimeMessage.setFrom("Biller@biller-app.com");
					mimeMessage.setRecipients(Message.RecipientType.TO, recipientAddressDms);
					mimeMessage.setRecipients(Message.RecipientType.CC, recipientAddressSlaCc);
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
		
		String [] pmoEmailIdlistArr = new String[0];
		String delegatedByEmailID = billerHelper.getUserEmailId(delegatedBy);
		String delegateToEmailID = "";
		InternetAddress[] recipientAddressDelegateCc;
		if(delegatedTo != null && !delegatedTo.equals("")) {
			delegateToEmailID = billerHelper.getUserEmailId(delegatedTo);
		}
		List<String> pmoEmailIdlist = userDao.getEmailListByRole(8);
		InternetAddress delegateToCc = null;
		InternetAddress[] delegateCcArr = new InternetAddress[1];
		
		if(pmoEmailIdlist != null) {
			pmoEmailIdlistArr = userDao.getEmailListByRole(8).toArray(new String [pmoEmailIdlist.size()]);
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
				
		try {
			if(delegateToEmailID!= null && !delegateToEmailID.equals("")) {
				delegateToCc = new InternetAddress(delegateToEmailID.trim());
				delegateCcArr[0] = delegateToCc;
			}else {
				delegateCcArr[0] = null;
			}
		} catch (AddressException e) {
			logger.error("Error while creating internet address", e);
		}
		
		
		if (delegateCcArr[0] == null) {
			recipientAddressDelegateCc  =  recipientAddressPmo;
		}else {
			recipientAddressDelegateCc = BillerUtil.joinArrayGeneric(delegateCcArr, recipientAddressPmo);
		}
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setFrom("Biller@biller-app.com");
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(delegatedByEmailID));
				mimeMessage.addRecipients(Message.RecipientType.CC, recipientAddressDelegateCc);
				if(delegationStatus.equals("SET")) {
					String delegatedToEmailID = billerHelper.getUserEmailId(delegatedTo);
					mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(delegatedToEmailID));
					mimeMessage.setText("Delegation " + delegationStatus + " By " + delegatedBy + " To " + delegatedTo);
				} else if(delegationStatus.equals("UNSET")) {
					mimeMessage.setText("Delegation " + delegationStatus + " By " + delegatedByEmailID);
				}
				mimeMessage.setSubject("Biller Notification: Delegation " + delegationStatus + " By " + delegatedBy);	
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
