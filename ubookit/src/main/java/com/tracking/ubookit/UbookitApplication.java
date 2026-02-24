package com.tracking.ubookit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the UBookIt order tracking application.
 * Bootstraps the Spring Boot context and starts the embedded web server.
 */
@SpringBootApplication
public class UbookitApplication {

	public static void main(String[] args) {
		SpringApplication.run(UbookitApplication.class, args);
	}

}
