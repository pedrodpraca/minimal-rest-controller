package br.uva.model.user;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.uva.model.user.User;
import br.uva.model.user.Role;
import br.uva.model.user.UserRepository;
import br.uva.model.user.RoleRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	@Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }
    @Override
    public void updateUser(User user){
        saveUser(user);
    }
    @Override
    public void deleteUserById(Long id){
        userRepository.delete(id);
    }
    @Override
    public void deleteAllUsers(){
        userRepository.deleteAll();
    }
    @Override
    public Iterable<User> findAllUsers(){
        return userRepository.findAll();
    }
    @Override
    public boolean isUserExist(User user) {
        return findByUsername(user.getUsername()) != null;
    }
    @Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		user = userRepository.save(user);
	}
}