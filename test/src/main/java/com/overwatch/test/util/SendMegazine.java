package com.overwatch.test.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMegazine {

	public SendMegazine() {

	}

	public void sendMegazine(String email) {
		String title = "[OverWatch 6월호] 서브 마리너 흑콤을 단 돈 500만원으로 살 수 있다고?";



		String host = "smtp.naver.com";
		final String user = "bluepack701@naver.com";
		final String password = "bluebluepack";
		
		String to = email;

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587"); //587
		props.put("mail.enable.ssl", "true");
		props.put("mail.user", user);
		props.put("mail.password", password);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", "smtp.naver.com");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		// Compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Subject
			message.setSubject(title);

			// Text
			message.setText(email+"님 저희 OverWatch 매거진을 구독해주셔서 감사합니다. 최신 할인 정보를 가장 빠르게 " +
					"알려드립니다. OverWatch 6월호 기대해주십시오~!");

			// send the message
			Transport.send(message);
			System.out.println("message sent successfully...");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
