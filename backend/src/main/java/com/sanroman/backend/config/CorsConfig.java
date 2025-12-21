package com.sanroman.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("*")
                        // 1. Permite todas las cabeceras (incluyendo Authorization y Content-Type)
                        .allowedHeaders("*") 
                        // 2. Permite el envío de cookies o credenciales de autenticación
                        .allowCredentials(true);
            }
        };
    }
}