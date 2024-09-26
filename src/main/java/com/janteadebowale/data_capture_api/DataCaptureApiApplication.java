package com.janteadebowale.data_capture_api;

import com.janteadebowale.data_capture_api.enums.Role;
import com.janteadebowale.data_capture_api.model.User;
import com.janteadebowale.data_capture_api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class DataCaptureApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataCaptureApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder){
		return args -> {
			if(!userRepository.isEmailExist("jante.adebowale@gmail.com")) {
				String password = passwordEncoder.encode("password");
				User user = new User();
				user.setFirstname("Jante");
				user.setLastname("Adebowale");
				user.setEmail("jante.adebowale@gmail.com");
				user.setPassword(password);
				user.setEnabled(true);
				user.setRole(Role.USER);
				user.setUsername("jante.adebowale@gmail.com");
				user.setId(UUID.randomUUID().toString());
				userRepository.registerUser(user);

			}
		};
	}

}
