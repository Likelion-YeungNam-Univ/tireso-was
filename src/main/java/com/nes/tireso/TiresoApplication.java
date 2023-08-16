package com.nes.tireso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TiresoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiresoApplication.class, args);
	}

}
