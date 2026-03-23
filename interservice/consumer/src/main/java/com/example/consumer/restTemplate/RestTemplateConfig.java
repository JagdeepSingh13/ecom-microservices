package com.example.consumer.restTemplate;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
// can't get RestTemplateBuilder

// can make multiple instances of provider and @LoadBalancer with redirect accordingly

@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced   // to resolve service name using service discovery
    public RestTemplate restTemplateBean() {
        return new RestTemplate();
    }

}
