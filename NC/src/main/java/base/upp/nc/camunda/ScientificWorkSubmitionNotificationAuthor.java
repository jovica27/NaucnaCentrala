package base.upp.nc.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import base.upp.nc.domain.User;
import base.upp.nc.service.MailService;
import base.upp.nc.service.UserService;

@Component
public class ScientificWorkSubmitionNotificationAuthor implements JavaDelegate {

	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		String mailSubject = "Scientific work submission email";
		
		Long authorId = Long.parseLong(execution.getVariable("authorId").toString());
		User author = userService.findById(authorId);
		String mailTextA = "Waiting for reviewers. You succesfully submitted an scientific work";
		mailService.sendEmail(mailSubject, mailTextA, author.getEmail());
	}

}