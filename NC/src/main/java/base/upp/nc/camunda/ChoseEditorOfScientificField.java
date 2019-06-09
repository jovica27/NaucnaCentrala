package base.upp.nc.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import base.upp.nc.domain.Magazine;
import base.upp.nc.repository.MagazineRepository;

@Component
public class ChoseEditorOfScientificField implements JavaDelegate {
    
    @Autowired
    private MagazineRepository magazineRepository;
 	
    @Override
    public void execute(DelegateExecution execution) throws Exception {
    	    	
        final Long magazineId = Long.valueOf(execution.getVariable("magazineId").toString());
        final Magazine magazine = magazineRepository.findById(magazineId).get();
        String editor= null;
        try {
        	editor= magazine.getScientificAreas().get(0).getEditor().toString();
         	execution.setVariable("scientificAreaEditorId", editor);
        }catch(Exception e) {
        	execution.setVariable("scientificAreaEditorId", magazine.getMainEditor().getId().toString());
        }

        System.out.println("Odabrao urednike no");
	}
}
