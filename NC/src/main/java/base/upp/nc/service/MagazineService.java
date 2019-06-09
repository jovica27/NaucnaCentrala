package base.upp.nc.service;

import java.util.List;

import base.upp.nc.domain.Magazine;

public interface MagazineService {

	List<Magazine> getAll();
	Magazine findOne(Long magazineId);
}
