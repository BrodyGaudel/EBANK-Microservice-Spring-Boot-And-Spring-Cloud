package com.brody.gestiondescomptes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;



@SpringBootApplication
@EnableFeignClients
public class GestionDesComptesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionDesComptesApplication.class, args);
	}

}
