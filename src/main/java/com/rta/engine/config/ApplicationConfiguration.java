package com.rta.engine.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ApplicationConfiguration - Spring Boot configuration class
 *
 * This class configures application-level settings such as CORS,
 * message converters, and other web configurations.
 */
@Configuration
public class ApplicationConfiguration implements WebMvcConfigurer {

    /**
     * Configure CORS (Cross-Origin Resource Sharing) settings
     * Allows frontend applications to communicate with this API
     *
     * @param registry CORS registry for configuration
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/algorithm/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}

