package com.foodservice.frontend.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

// Configuration class for WebClient
// This class is used to configure the WebClient bean
// It is used to create a WebClient bean that is used to make HTTP requests to the backend API
// APIs base URL is read from application.yml

@AllArgsConstructor
@Configuration
public class WebClientConfig {

    private final Environment env;


    @Bean
    public WebClient webClient() {


        String baseUrl = env.getProperty("api.baseUrl");
        if (baseUrl == null) {
            throw new RuntimeException("API base URL not found");
        }
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
