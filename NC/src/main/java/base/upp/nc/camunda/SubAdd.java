package base.upp.nc.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import base.upp.nc.domain.Magazine;
import base.upp.nc.domain.Subscription;
import base.upp.nc.domain.User;
import base.upp.nc.repository.MagazineRepository;
import base.upp.nc.repository.SubscriptionRepository;
import base.upp.nc.repository.UserRepository;
import base.upp.nc.service.MagazineService;
import base.upp.nc.service.UserService;

@Component
public class SubAdd implements JavaDelegate {
    
	@Autowired
    private SubscriptionRepository subscriptionRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private MagazineService magazineService;
	
    @Override
    public void execute(DelegateExecution execution) throws Exception {
    	Long authorId = Long.parseLong(execution.getVariable("authorId").toString());
		User author = userService.findById(authorId);

    	Long magazineId = Long.parseLong(execution.getVariable("magazineId").toString());
    	Magazine magazine = magazineService.findOne(magazineId);
    	
    	Subscription subscription = new Subscription();
    	subscription.setMagazine(magazine);
    	subscription.setUser(author);

    	subscriptionRepository.saveAndFlush(subscription);

        System.out.println("Sub. added");
    }
}
