package com.example.resttemplate_316;

import com.example.resttemplate_316.Service.RestTemplateService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        RestTemplateService restTemplateService = new RestTemplateService(new RestTemplate());
        restTemplateService.getCookieAndUser();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
