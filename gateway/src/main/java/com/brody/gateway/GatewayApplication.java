package com.brody.gateway;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
		
		CorsConfiguration corsConfiguration = new CorsConfiguration();
	    corsConfiguration.setAllowCredentials(true);
	    corsConfiguration.addAllowedOrigin("*");
	    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
	    corsConfiguration.addAllowedHeader("origin");
	    corsConfiguration.addAllowedHeader("content-type");
	    corsConfiguration.addAllowedHeader("accept");
	    corsConfiguration.addAllowedHeader("authorization");
	    corsConfiguration.addAllowedHeader("cookie");
		
	}
	
	/**
	 * configuration dynamique 
	 * @param rdc
	 * @param dlp
	 * @return
	 */
	@Bean
	DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp) {
		
		return new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
	}
	

	
}
