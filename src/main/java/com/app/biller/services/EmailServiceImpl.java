package com.app.biller.services;

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

@Service("emailService")
public class EmailServiceImpl implements EmailService {

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	JavaMailSender mailSender;

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
				mimeMessage.setFrom("vivekveera@gmail.com");
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("vivekveera@gmail.com"));
				mimeMessage.setText("Approved by " + mailObj);
				mimeMessage.setSubject("Biller Data Approval Notice");
			}
		};
		return preparator;
	}
}
