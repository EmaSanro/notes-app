package com.sanroman.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		// Automatically searches for the .env file in the working directory (project root)
		Dotenv dotenv = Dotenv.load(); 

        // It iterates over the loaded variables and sets them as system properties for Spring Boot to read.
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
		SpringApplication.run(BackendApplication.class, args);
	}

}
