package com.myproject.registerApi.controller;

import com.myproject.registerApi.config.TestConfig;
import com.myproject.registerApi.model.ActivityDTO;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

import static com.myproject.registerApi.controller.constants.ActivityControllerConstants.*;
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

    @Autowired
    private WebClient webClient;

    @Autowired
    private ActivityController activityController;

    @Autowired
    private MockWebServer mockWebServer;

    @Test
    public void createActivityControllerTest() {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(201)
                        .setHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .setBody(ACTIVITY_BODY)
        );

        ActivityDTO testActivity = webClient.post()
                .uri(ACTIVITY_ENDPOINT)
                .bodyValue(new ActivityDTO().name(TEST))
                .retrieve()
                .bodyToFlux(ActivityDTO.class)
                .blockFirst();

        assertNotNull(testActivity);
        assertEquals(testActivity.getId(), UUID.fromString(ACTVITY_UUID_TEST_STRING));
        assertEquals(testActivity.getName(), TEST);
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
        assertEquals(activityDTO.getId(), UUID.fromString(ACTVITY_UUID_TEST_STRING));
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
