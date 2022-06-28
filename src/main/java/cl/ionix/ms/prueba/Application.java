package cl.ionix.ms.prueba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({ @PropertySource("classpath:bbdd.properties") })
public class Application {

	/**
	 * Main routine - It starts the application context
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}