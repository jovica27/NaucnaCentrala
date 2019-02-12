package root.demo.services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import helpers.PasswordUtil;
import root.demo.model.User;
import root.demo.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User save(User user){
		
		try {
			String password = PasswordUtil.getPasswordHash(user.getPassword());
			user.setPassword(password);
			user.setCreatedDate(new Date(System.currentTimeMillis()));
			
			return userRepository.save(user);
			
		} catch(Exception e) {
			
			return null;
		}	
	
	}

	@Override
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmailIgnoreCase(email);
	}
}
