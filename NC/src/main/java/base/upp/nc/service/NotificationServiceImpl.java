package base.upp.nc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements MailService {

	private final String loadingMessage = "Sending notification...";
	private final String successMessage = "Mail successfully sent.";
	private final String errorMessage = "Error occured while sending mail.";
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment environment;
	
	@Override
	public void sendEmail(String subject, String content, String receiverMail) {
		
		SimpleMailMessage mailA = new SimpleMailMessage();
		mailA.setTo(receiverMail);
		mailA.setFrom(environment.getProperty("spring.mail.username"));
		mailA.setSubject(subject);
		mailA.setText(content);
		
		System.out.println(loadingMessage);
		try {
			javaMailSender.send(mailA);
			System.out.println(successMessage);
		} catch (Exception e) {
			System.out.println(errorMessage);
		}
		
	}

}
