package recipes.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import recipes.models.Role;
import recipes.models.RoleEnum;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	Optional<Role> findByName(RoleEnum name);
	
	boolean existsByName(RoleEnum name);
	
}
