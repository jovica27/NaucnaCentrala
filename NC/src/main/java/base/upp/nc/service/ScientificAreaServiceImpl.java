package base.upp.nc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.upp.nc.domain.ScientificArea;
import base.upp.nc.repository.ScientificAreaRepository;

@Service
public class ScientificAreaServiceImpl implements ScientificAreaService {

	@Autowired
	private ScientificAreaRepository scientificAreaRepository;
	
	public List<ScientificArea> getAll() {
		return scientificAreaRepository.findAll();
	}
	
}
