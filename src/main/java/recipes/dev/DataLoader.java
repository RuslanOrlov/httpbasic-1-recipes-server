package recipes.dev;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import recipes.models.RoleEnum;
import recipes.services.RoleService;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
	
	private final RoleService roleService;
	
	@Override
	public void run(String... args) throws Exception {
		
		roleService.createRole(RoleEnum.ROLE_GUEST);
		roleService.createRole(RoleEnum.ROLE_USER);
		roleService.createRole(RoleEnum.ROLE_ADMIN);
		
	}

}
