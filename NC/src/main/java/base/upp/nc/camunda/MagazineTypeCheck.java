package base.upp.nc.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import base.upp.nc.domain.Magazine;
import base.upp.nc.domain.PaymentType;
import base.upp.nc.repository.MagazineRepository;

@Component
public class MagazineTypeCheck implements JavaDelegate {

    @Autowired
    private MagazineRepository magazineRepository;
    
    @Override
    public void execute(DelegateExecution execution) throws Exception {

        final Long magazineId = Long.parseLong( execution.getVariable("magazineId").toString());
        final Magazine magazine = magazineRepository.findById(magazineId).get();

        if(magazine.getPaymentType().equals(PaymentType.OPENACCESS)){
            execution.setVariable("isOpenAccess", true);
        } else {
            execution.setVariable("isOpenAccess", false);
        }
    }
}
