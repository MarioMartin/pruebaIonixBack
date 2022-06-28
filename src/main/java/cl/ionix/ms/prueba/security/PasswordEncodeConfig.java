package cl.ionix.ms.prueba.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncodeConfig {

	@Bean
	public BCryptPasswordEncoder PasswordEncodeConfig() {

		return new BCryptPasswordEncoder();

	}

}
