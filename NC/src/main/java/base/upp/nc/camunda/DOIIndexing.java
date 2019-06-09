package base.upp.nc.camunda;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import base.upp.nc.domain.ScientificPaper;
import base.upp.nc.domain.User;
import base.upp.nc.repository.ScientificAreaRepository;
import base.upp.nc.service.MagazineService;
import base.upp.nc.service.ScientificPaperService;
import base.upp.nc.service.UserService;

@Component
public class DOIIndexing implements JavaDelegate {
    
	@Autowired
	private UserService userService;
	
	@Autowired
	private ScientificAreaRepository scientificAreaRepository;
	
	@Autowired
	private MagazineService magazineService;
	
	@Autowired
	private ScientificPaperService scientificPaperService;
	
//	@Autowired
//	private ScientificPaperElasticSearchRepository elasticRepository;
 	
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("indeksiranje doia");	

	}
}
