package recipes.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import recipes.models.Role;
import recipes.models.RoleEnum;
import recipes.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
	
	private final RoleRepository roleRepository;
	
	public Role createRole(RoleEnum roleEnum) {
		if (!roleRepository.existsByName(roleEnum)) {
			return roleRepository.save(Role.builder().name(roleEnum).build());
		}
		return null;
	}
	
	public Role getRole(String roleType) {
		RoleEnum roleEnum = null;
		if (roleType == null) {
			roleEnum = RoleEnum.ROLE_GUEST;
		} else {
			switch (roleType) {
			case "user":
				roleEnum = RoleEnum.ROLE_USER;
				break;
			case "admin":
				roleEnum = RoleEnum.ROLE_ADMIN;
				break;
			default:
				roleEnum = RoleEnum.ROLE_GUEST;
				break;
			}
		}
		Role role = null;
		if (roleRepository.existsByName(roleEnum)) {
			role = roleRepository.findByName(roleEnum).get();
		}
		return role;
	}
	
}
