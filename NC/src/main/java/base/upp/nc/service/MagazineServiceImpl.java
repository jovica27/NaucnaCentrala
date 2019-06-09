package base.upp.nc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.upp.nc.domain.Magazine;
import base.upp.nc.repository.MagazineRepository;

@Service
public class MagazineServiceImpl implements MagazineService {

	@Autowired
	private MagazineRepository magazineRepository;
	
	@Override
	public List<Magazine> getAll() {
		return magazineRepository.findAll();
	}

	@Override
	public Magazine findOne(Long magazineId) {
		return magazineRepository.findById(magazineId).get();
	}

}
