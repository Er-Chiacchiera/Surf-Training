package it.uniroma3.siw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
// @EntityScan("it.uniroma3.siw.model")
public class SurfApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurfApplication.class, args);
	}

}
