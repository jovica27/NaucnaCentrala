package root.demo.handlers;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import root.demo.dto.FormSubmissionDto;
import root.demo.model.User;
import root.demo.services.IUserService;

@Component
public class RegisterHandler implements JavaDelegate {
	
    @Autowired
    private IUserService userService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<FormSubmissionDto> registerData =
            (List<FormSubmissionDto>)execution.getVariable("registerData");


        User u = validateUser(registerData);
        
        if (u == null) {
            System.out.println("Neuspesna reg.");
            execution.setVariable("currentUser", u);
        } else {
            System.out.println("Uspesna reg.");

            System.out.println("Uloguj korisnika");
            execution.setVariable("currentUser", u);
        }

    }

    private User validateUser(List<FormSubmissionDto> registrationData) {
        String email = null;
        String password = null;
        String firstName = null;
        String lastName = null;
        String role = null;
        String phoneNumber = null;

        for (FormSubmissionDto formField : registrationData) {
        	
            if(formField.getFieldId().equals("email")) {
                email = formField.getFieldValue();
            }
            if(formField.getFieldId().equals("password")) {
                password = formField.getFieldValue();
            }

            if(formField.getFieldId().equals("firstName")) {
                firstName = formField.getFieldValue();
            }

            if(formField.getFieldId().equals("lastName")) {
                lastName = formField.getFieldValue();      
            }
            
            if(formField.getFieldId().equals("role")) {
            	role = formField.getFieldValue();      
            }
            
            if(formField.getFieldId().equals("phoneNumber")) {
            	phoneNumber = formField.getFieldValue();      
            }
            
        }
        
        User user = new User(firstName, lastName, email, password, true, role, phoneNumber);

        return userService.save(user);

    }
}
