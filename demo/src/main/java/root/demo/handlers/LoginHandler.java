package root.demo.handlers;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import root.demo.repository.UserRepository;


public class LoginHandler implements JavaDelegate {
	
    @Autowired
    private UserRepository userRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {

    	
    	System.out.println("Login handler pozvan");
    	

    }

}
