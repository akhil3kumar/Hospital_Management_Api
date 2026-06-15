package com.akhil.patient_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // ✅ This is for CORS
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/patients/**") // apply to all API endpoints
                .allowedOrigins("http://localhost:4200","http://localhost:8080") // Angular dev server
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

}