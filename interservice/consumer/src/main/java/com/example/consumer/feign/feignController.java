package com.example.consumer.feign;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feign")
@RequiredArgsConstructor
public class feignController {

    private final ProviderfeignClient providerfeignClient;

    @GetMapping("/instance")
    public String getInstance() {
        return providerfeignClient.getInstanceInfo();
    }

}
