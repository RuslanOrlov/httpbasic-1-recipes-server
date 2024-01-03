package recipes.services;

import java.util.Base64;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import recipes.dtos.AuthRequest;
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
					.body("Registration error: User with given username already exists in database.");
		} else if (userRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest()
					.body("Registration error: User with given email already exists in database.");
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

	public Boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public Boolean isCorrectPassowrd(AuthRequest request) {
		if (existsByUsername(request.getUsername())) {
			String rawPassword = request.getPassword();
			String encodedPassword = getUserByUsername(request.getUsername()).getPassword();
			return encoder.matches(rawPassword, encodedPassword);
		}
		return false;
	}

	public ResponseEntity<?> checkUsernamePassword(AuthRequest request) {
		if (!existsByUsername(request.getUsername())) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Authentication error: Database has no user with name '" + request.getUsername() + "'.");
		} else if (!isCorrectPassowrd(request)) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Authentication error: Incorrect password specified for user '" + request.getUsername() + "'.");
		}
		return ResponseEntity.ok("Message: User's Username and Password are correct.");		
	}

	public String getAuthHeader(AuthRequest request) {
		String source = request.getUsername() + ":" + request.getPassword();
		String base64Credentials = Base64.getEncoder().encodeToString(source.getBytes());
		String authHeader = "Basic " + base64Credentials;
		return authHeader;
	}
}
