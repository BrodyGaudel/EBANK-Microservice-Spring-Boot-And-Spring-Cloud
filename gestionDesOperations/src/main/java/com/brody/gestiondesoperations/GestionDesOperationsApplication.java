package com.brody.gestiondesoperations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GestionDesOperationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionDesOperationsApplication.class, args);
	}

}
