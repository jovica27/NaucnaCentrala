package root.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import root.demo.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmailIgnoreCase(String username);

}
