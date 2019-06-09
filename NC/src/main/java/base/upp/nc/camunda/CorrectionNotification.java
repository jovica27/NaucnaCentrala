package base.upp.nc.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import base.upp.nc.domain.User;
import base.upp.nc.service.EmailService;
import base.upp.nc.service.MailService;
import base.upp.nc.service.UserService;

@Component
public class CorrectionNotification implements JavaDelegate {
    
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService notificationService;
 	
    @Override
    public void execute(DelegateExecution execution) throws Exception {
    	
    	
    	Long authorId = Long.parseLong(execution.getVariable("authorId").toString());
    	User author = userService.findById(authorId);
    	
    	String mailSubject = "Correction notification email";
    	String mailText = "Correct  mistakes in your scientific work.";
    	notificationService.sendEmail(mailSubject, mailText, author.getEmail());

	}
}
