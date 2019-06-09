package base.upp.nc.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import base.upp.nc.domain.User;
import base.upp.nc.service.EmailService;
import base.upp.nc.service.UserService;

@Component
public class ReviewProcessTimedOutNotification implements JavaDelegate {
    
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
 	
    @Override
    public void execute(DelegateExecution execution) throws Exception {
    	
    	Long mainEditorId = Long.parseLong(execution.getVariable("mainEditorId").toString());
    	User mainEditor = userService.findById(mainEditorId);
		SimpleMailMessage MainEditorEmail = new SimpleMailMessage();
		
		MainEditorEmail.setTo(mainEditor.getEmail());
		MainEditorEmail.setSubject("\"Time for review is over");
		MainEditorEmail.setText("Time for reviewing the scientific work timed out");
		MainEditorEmail.setFrom("noreply@domain.com");

		emailService.sendEmail(MainEditorEmail);
    	
	}
}
