package base.upp.nc.service;

public interface MailService {

	void sendEmail(String subject, String content, String receiverMail);
}
