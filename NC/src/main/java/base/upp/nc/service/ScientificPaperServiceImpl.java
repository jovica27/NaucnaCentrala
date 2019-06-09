package base.upp.nc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.upp.nc.domain.ScientificPaper;
import base.upp.nc.repository.ScientificPaperRepository;

@Service
public class ScientificPaperServiceImpl implements ScientificPaperService {

	@Autowired
	private ScientificPaperRepository scientificPaperRepository;

	@Override
	public void save(ScientificPaper sp) {
		scientificPaperRepository.save(sp);
	}
		
}
