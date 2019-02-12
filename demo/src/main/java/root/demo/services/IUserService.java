package root.demo.services;

import java.util.List;

import root.demo.model.User;


public interface IUserService {

	User save(User user);

	List<User> findAll();

	User getUserByEmail(String email);

}
