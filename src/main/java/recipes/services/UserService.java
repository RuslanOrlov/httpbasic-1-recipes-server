package recipes.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import recipes.dtos.RegisterRequest;
import recipes.models.User;
import recipes.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder encoder;
	private final RoleService roleService;
	
	public ResponseEntity<?> registerUser(RegisterRequest request) {
		if (userRepository.existsByUsername(request.getUsername())) {
			return ResponseEntity.badRequest()
					.body("Error: User with given username already exists in database.");
		} else if (userRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest()
					.body("Error: User with given email already exists in database.");
		}
		User user = User.builder()
				.username(request.getUsername())
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(encoder.encode(request.getPassword()))
				.roles(List.of(roleService.getRole(request.getRoleType())))
				.build();
		userRepository.save(user);
		return ResponseEntity.ok("Message: User '" + user.getUsername() + "' successfully created!");
	}
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}
}
