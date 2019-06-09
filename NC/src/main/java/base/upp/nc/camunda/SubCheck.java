package base.upp.nc.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import base.upp.nc.domain.Subscription;
import base.upp.nc.domain.User;
import base.upp.nc.repository.MagazineRepository;
import base.upp.nc.repository.SubscriptionRepository;


@Component
public class SubCheck implements JavaDelegate {
    
	@Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
    	//TODO: for now just check if subscription entry exists later check dates.
    	Long authorId = Long.parseLong(execution.getVariable("authorId").toString());
    	Subscription subscription = subscriptionRepository.findOneByUser(authorId);
    	if(subscription != null) {
            execution.setVariable("isSubscriptionPayed", true);
    	}else {
            execution.setVariable("isSubscriptionPayed", false);
    	}
        System.out.println("Sub. checked");
    }
}
