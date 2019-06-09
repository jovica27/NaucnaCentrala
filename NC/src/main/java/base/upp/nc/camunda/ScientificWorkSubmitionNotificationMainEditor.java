package base.upp.nc.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import base.upp.nc.domain.User;
import base.upp.nc.service.MailService;
import base.upp.nc.service.UserService;

@Component
public class ScientificWorkSubmitionNotificationMainEditor implements JavaDelegate {

	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		String mailSubject = "Scientific work submission email";
		
		Long mainEditorId = Long.parseLong(execution.getVariable("mainEditorId").toString());
		User mainEditor = userService.findById(mainEditorId);
		String mailTextE = "New article is waiting for you";
		mailService.sendEmail(mailSubject, mailTextE, mainEditor.getEmail());

	}

}
