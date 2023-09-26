package com.myproject.registerApi.config;

import com.myproject.registerApi.repository.ActivityRepository;
import com.myproject.registerApi.service.ActivityService;
import com.myproject.registerApi.service.ActivityServiceImpl;
import okhttp3.mockwebserver.MockWebServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class TestConfig {

    @Bean
    public MockWebServer mockWebServer() {
        return new MockWebServer();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:" + mockWebServer().getPort())
                .build();
    }
}
