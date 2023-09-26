package com.myproject.registerApi.controller;

import com.myproject.registerApi.config.TestConfig;
import com.myproject.registerApi.model.ActivityDTO;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TestConfig.class)
public class ActivityRestControllerTest {

    private static final String ACTIVITY_BODY = """
                                {
                                    "id": "dbe1625c-adca-4c95-af41-7eb4ef8724ef",
                                    "name": "test"
                                }
                                """;

    @Autowired
    private WebClient webClient;

    @Autowired
    private ActivityController activityController;

    @Autowired
    private MockWebServer mockWebServer;

    @BeforeEach
    public void setUp() throws IOException {
        mockWebServer.start();
    }

    @AfterEach
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void activityControllerTest() throws Exception {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(201)
                        .setHeader("Content-Type", "application/json")
                        .setBody(ACTIVITY_BODY)
        );



        ActivityDTO test = webClient.post()
                .uri("/activity")
                .bodyValue(new ActivityDTO().name("test"))
                .retrieve()
                .bodyToFlux(ActivityDTO.class)
                .blockFirst();



        assertNotNull(test);
        assertEquals(test.getId(), UUID.fromString("dbe1625c-adca-4c95-af41-7eb4ef8724ef"));
        assertEquals(test.getName(), "test");
    }

    @Test
    public void getActivityByNameTest() throws Exception {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader("Content-Type", "application/json")
                        .setBody(ACTIVITY_BODY)
        );

        ActivityDTO activityDTO = webClient.get()
                .uri("/activity/{name}", "test")
                .retrieve()
                .bodyToFlux(ActivityDTO.class)
                .blockFirst();

        assertNotNull(activityDTO);
        assertEquals(activityDTO.getId(), UUID.fromString("dbe1625c-adca-4c95-af41-7eb4ef8724ef"));
        assertEquals(activityDTO.getName(), "test");
    }


}
