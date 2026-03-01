package proyecto.proj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicacion Spring Boot.
 * Inicia el servidor REST para la gestion de ventas de videojuegos.
 */
@SpringBootApplication
public class ProjApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjApplication.class, args);
	}

}
