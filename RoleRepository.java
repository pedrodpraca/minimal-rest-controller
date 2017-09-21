package br.uva.model.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.uva.model.user.Role;


@Repository("roleRepository")
public interface RoleRepository extends CrudRepository<Role, Integer>{
	Role findByRole(String role);

}