package com.myproject.registerApi.repository;

import com.myproject.registerApi.model.ActivityDTO;
import com.myproject.registerApi.model.db.ActivityDb;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext
@ActiveProfiles("test")
public class ActivityRepositoryIntegrationTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    public void saveActivity() {
        ActivityDb producedActivityDb = activityRepository.save(new ActivityDb("test"));

        ActivityDb fetchedActivityDb = activityRepository.getByName("test").get();

        assertEquals(producedActivityDb, fetchedActivityDb);
    }
}
