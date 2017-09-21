package br.uva.model.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.uva.model.user.User;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
	 User findByUsername(String username);
	 User findByEmail(String email);
}