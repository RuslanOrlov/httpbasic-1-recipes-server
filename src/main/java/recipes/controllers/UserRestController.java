package recipes.controllers;

import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import recipes.dtos.AuthRequest;
import recipes.dtos.RegisterRequest;
import recipes.models.User;
import recipes.services.UserService;

@RestController
@RequestMapping(path = "/api/v1/users", produces = "application/json")
@RequiredArgsConstructor
public class UserRestController {
	
	private final UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
		return userService.registerUser(request);
	}
	
	@GetMapping(params = "username")
	public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
		User user = userService.getUserByUsername(username);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(user);
	}
	
	@GetMapping(params = "email")
	public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
		User user = userService.getUserByEmail(email);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(user);
	}
	
	@GetMapping(params = {"existsusername"})
	public Boolean existsByUsername(@RequestParam String existsusername) {
		return userService.existsByUsername(existsusername);
	}
	
	@GetMapping(params = "existsemail")
	public Boolean existsByEmail(@RequestParam String existsemail) {
		return userService.existsByEmail(existsemail);
	}
	
	@PostMapping("/checkpassword")
	public Boolean isCorrectPassowrd(@RequestBody AuthRequest request) {
		return userService.isCorrectPassowrd(request);
	}
	
	@PostMapping("/token")
	public String getAuthHeader(@RequestBody AuthRequest request) {
		String source = request.getUsername() + ":" + request.getPassword();
		String base64Credentials = Base64.getEncoder().encodeToString(source.getBytes());
		String authHeader = "Basic " + base64Credentials;
		return authHeader;
	}
}
