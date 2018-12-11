package com.aiproj.ai_projekt.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSender {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	public void sendEmail(String title, String content) {
		MimeMessage mail = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setTo("aplikacje.internetowe2018@wp.pl");
			helper.setReplyTo("aplikacje.internetowe2018@wp.pl");
			helper.setFrom("aplikacje.internetowe2018@wp.pl");
			helper.setSubject(title);
			helper.setText(content, true);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		javaMailSender.send(mail);
	}
}