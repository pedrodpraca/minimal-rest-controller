package br.uva.model.user;

import br.uva.model.user.User;

public interface UserService {
    public User findByUsername(String username);

    public User findById(Long id);
 
    public void updateUser(User user);
 
    public void deleteUserById(Long id);
 
    public void deleteAllUsers();
 
    public Iterable<User> findAllUsers();
 
    public boolean isUserExist(User user);
	
	public void saveUser(User user);
}