package recipes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import recipes.models.User;
import recipes.services.UserService;

@Configuration
public class SecurityConfig {
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	UserDetailsService userDetailsService(UserService userService) {
		
		UserDetailsService service = new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String username) 
					throws UsernameNotFoundException {
				
				User user = userService.getUserByUsername(username);
				
				if (user == null) {
					throw new UsernameNotFoundException("User '" + username + "' not found in database");
				}
				
				return UserDetailsImpl
						.builder()
						.id(user.getId())
						.username(user.getUsername())
						.firstname(user.getFirstname())
						.lastname(user.getLastname())
						.email(user.getEmail())
						.password(user.getPassword())
						.authorities(UserDetailsImpl.converToAuthorities(user.getRoles()))
						.build();
			}
		};
		
		return service;
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) -> csrf.disable())
			.cors(Customizer.withDefaults())
			.authorizeHttpRequests((auth) -> 
					auth.requestMatchers("/api/v1/users/**")
						.permitAll()
						.anyRequest()
						.authenticated()
			)
			.httpBasic(Customizer.withDefaults());
		return http.build();
	}
}
