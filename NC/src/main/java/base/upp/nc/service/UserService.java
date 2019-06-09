package base.upp.nc.service;

import base.upp.nc.domain.User;

public interface UserService {

	User findByEmail(String email);
	User findById(Long id);

}
