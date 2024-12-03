package com.election.electionbackend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class APIConfig implements WebMvcConfigurer {

    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.passphrase}")
    private String passphrase;
    @Value("${jwt.duration-of-validity}")
    private int tokenDurationOfValidity;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // Exact origin of your frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true); // Allow credentials if necessary
    }

    public String getIssuer() {
        return issuer;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public int getTokenDurationOfValidity() {
        return tokenDurationOfValidity;
    }
}
