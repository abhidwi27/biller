package com.app.biller.services;

import static com.app.biller.util.BillerHelper.getUserEmailId;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

	private MimeMessagePreparator getFileUploadMessagePreparator(String dataType, String billCycle, String weekEnd) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				mimeMessage.setFrom("biller@biller-app.com");
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(leadsMailId));
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(dmMailId));
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(bamMailId));
				mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(pmoMailId));
				mimeMessage.setText("New "+ dataType + " Data is Updated for Bill Cycle: "+ billCycle);
				mimeMessage.setSubject("Biller Notification: New " + dataType + " Data for " + billCycle + " Uploaded.");
			}
		};
		return preparator;
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
	public void sendFileUploadEmail(String dataType, String billCycle, String weekEnd) {
		MimeMessagePreparator preparator = getFileUploadMessagePreparator(dataType, billCycle, weekEnd);
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
