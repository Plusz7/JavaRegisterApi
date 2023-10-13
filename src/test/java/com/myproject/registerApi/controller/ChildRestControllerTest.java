package com.myproject.registerApi.controller;

import com.myproject.registerApi.config.TestConfig;
import com.myproject.registerApi.model.ActivityDTO;
import com.myproject.registerApi.model.ChildDTO;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.myproject.registerApi.controller.constants.ChildControllerConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureWebTestClient
public class ChildRestControllerTest {

    @Autowired


    private static final String CHILD_BODY = """
            {
                "id": "84c6a46d-df45-4d53-a0ef-7ad8c948dbb1",
                "firstName": "firstname",
                "lastName": "string",
                "age": 0,
                "gender": "MALE",
                "socialNumber": "string",
                "activities": [
                    {
                        "id": "b385b5af-16f2-429b-9976-40646ade3f3d",
                        "name": "test"
                    }
                ]
            }
                                """;

    private ChildDTO childTestObject = new ChildDTO()
            .firstName("string")
            .lastName("string")
            .age(0)
            .gender("MALE")
            .socialNumber("string")
            .activities(Collections.singletonList(new ActivityDTO()
                            .id(UUID.fromString("b385b5af-16f2-429b-9976-40646ade3f3d"))
                            .name(TEST)
                    )
            );

    @Autowired
    private WebClient webClient;

    @Autowired
    private ChildController childController;

    @Autowired
    private MockWebServer mockWebServer;

    @Test
    public void createChildControllerTest() {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(201)
                        .setHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .setBody(CHILD_BODY)
        );

        ChildDTO testChild = webClient.post()
                .uri(CHILD_ENDPOINT)
                .bodyValue(childTestObject)
                .retrieve()
                .bodyToFlux(ChildDTO.class)
                .blockFirst();

        assertNotNull(testChild);
        assertEquals(testChild.getId(), UUID.fromString(CHILD_UUID_TEST_STRING));
        assertEquals(testChild.getFirstName(), CHILD_FIRST_NAME_STRING);
    }

    @Test
    public void getChildById() {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .setBody(CHILD_BODY)
        );

        ChildDTO childDto = webClient.get()
                .uri(CHILD_ENDPOINT + "/{id}", CHILD_UUID_TEST_STRING)
                .retrieve()
                .bodyToFlux(ChildDTO.class)
                .blockFirst();

        assertNotNull(childDto);
        assertEquals(childDto.getId(), UUID.fromString(CHILD_UUID_TEST_STRING));
        assertEquals(childDto.getFirstName(), CHILD_FIRST_NAME_STRING);
    }

    @Test
    public void getAllChild() {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .setBody("[" + CHILD_BODY + "," + CHILD_BODY +"]")
        );

        List<ChildDTO> childDTOList = webClient.get()
                .uri(CHILD_ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ChildDTO.class)
                .collectList()
                .block();

        assertNotNull(childDTOList);
        assertEquals(childDTOList.size(), 2);
    }


}
