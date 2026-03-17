package com.example.consumer.restTemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
//can't get RestTemplateBuilder

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplateBean() {
        return new RestTemplate();
    }

}
