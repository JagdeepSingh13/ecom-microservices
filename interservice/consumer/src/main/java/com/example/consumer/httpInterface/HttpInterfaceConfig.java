package com.example.consumer.httpInterface;

// can use different clients such as rest or web client

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class HttpInterfaceConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    Don't make multiple beans that return same thing as restTemplate or webClient
//    as already done in their services

//    @Bean
//    @LoadBalanced
//    public WebClient.Builder webClientBuilder() {
//        return WebClient.Builder();
//    }

    @Bean
    public ProviderHttpInterface webClientHttpInterface(RestTemplate restTemplate) {
//        WebClient webClient = WebClient.builder()
//                .baseUrl("http://localhost:8081")
//                .build();
//
//        WebClientAdapter adapter = WebClientAdapter.create(webClient);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
//
//        ProviderHttpInterface httpInterface = factory.createClient(ProviderHttpInterface.class);
//        return httpInterface;

        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://provider"));
        RestTemplateAdapter adapter = RestTemplateAdapter.create(restTemplate);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        ProviderHttpInterface service = factory.createClient(ProviderHttpInterface.class);
        return service;
    }

}
