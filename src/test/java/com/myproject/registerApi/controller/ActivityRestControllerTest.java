package com.myproject.registerApi.controller;

import com.myproject.registerApi.config.TestConfig;
import com.myproject.registerApi.model.ActivityDTO;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureWebTestClient // no need for beforeEach and afterEach
public class ActivityRestControllerTest {

    private static final String ACTIVITY_BODY = """
                                {
                                    "id": "dbe1625c-adca-4c95-af41-7eb4ef8724ef",
                                    "name": "test"
                                }
                                """;

    private static final String TEST = "test";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String UUID_STRING = "dbe1625c-adca-4c95-af41-7eb4ef8724ef";

    private static final String ACTIVITY_ENDPOINT = "/activity";

    @Autowired
    private WebClient webClient;

    @Autowired
    private ActivityController activityController;

    @Autowired
    private MockWebServer mockWebServer;

    @Test
    public void activityControllerTest() {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(201)
                        .setHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .setBody(ACTIVITY_BODY)
        );

        ActivityDTO test = webClient.post()
                .uri(ACTIVITY_ENDPOINT)
                .bodyValue(new ActivityDTO().name(TEST))
                .retrieve()
                .bodyToFlux(ActivityDTO.class)
                .blockFirst();

        assertNotNull(test);
        assertEquals(test.getId(), UUID.fromString(UUID_STRING));
        assertEquals(test.getName(), TEST);
    }

    @Test
    public void getActivityByNameTest() {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .setBody(ACTIVITY_BODY)
        );

        ActivityDTO activityDTO = webClient.get()
                .uri(ACTIVITY_ENDPOINT + "/{name}", TEST)
                .retrieve()
                .bodyToFlux(ActivityDTO.class)
                .blockFirst();

        assertNotNull(activityDTO);
        assertEquals(activityDTO.getId(), UUID.fromString(UUID_STRING));
        assertEquals(activityDTO.getName(), TEST);
    }

    @Test
    public void getAllActivitiesTest() {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .setBody("[" + ACTIVITY_BODY + "]")
        );

        List<ActivityDTO> list = webClient.get()
                .uri(ACTIVITY_ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ActivityDTO.class)
                .collectList()
                .block();

        assertNotNull(list);
        assertEquals(list.size(), 1);
    }
        //TODO
    @Test
    public void testUpdateActivity() {

    }

    @Test
    public void testDeleteActivity() {

    }
}
