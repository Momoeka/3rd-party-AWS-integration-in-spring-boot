package com.weather.mausam.Controller.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class StringConfiguration {

    @Bean
    RestTemplate getRestTemplateObj() {
        return new RestTemplate();
    }

    @Bean
    ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

}
