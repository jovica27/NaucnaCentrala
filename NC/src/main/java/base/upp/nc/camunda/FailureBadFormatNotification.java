package base.upp.nc.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import base.upp.nc.domain.User;
import base.upp.nc.service.MailService;
import base.upp.nc.service.UserService;

@Component
public class FailureBadFormatNotification implements JavaDelegate {
    
	@Autowired
	private UserService userService;
 	
	@Autowired
	private MailService notificationService;
	
    @Override
    public void execute(DelegateExecution execution) throws Exception {
    	
    	Long authorId = Long.parseLong(execution.getVariable("authorId").toString());
    	User author = userService.findById(authorId);
    	String mailSubject = "Scientific work rejected";
    	String mailContent = "We are sorry, your scientific wrok is rejected because time for fixing format run out.";
    	notificationService.sendEmail(mailSubject, mailContent, author.getEmail());

	}
}
