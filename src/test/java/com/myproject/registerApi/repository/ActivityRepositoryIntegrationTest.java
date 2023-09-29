package com.myproject.registerApi.repository;

import com.myproject.registerApi.model.db.ActivityDb;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureMockMvc //TODO maybe delete ?
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class ActivityRepositoryIntegrationTest {

    private static final String TEST_ACTIVITY_NAME = "test";

    private static final Logger logger = LoggerFactory.getLogger(ActivityRepositoryIntegrationTest.class);

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    @Transactional
    public void shouldSaveAndRetrieveActivity() {
        // Step 1: Save a new activity
        ActivityDb savedActivity = activityRepository.save(new ActivityDb(TEST_ACTIVITY_NAME));

        // Step 2: Log information for debugging
        logger.info("Saved activity ID: {}", savedActivity.getId());

        // Step 3: Retrieve the saved activity
        ActivityDb retrievedActivity = activityRepository.findById(savedActivity.getId())
                .orElseThrow(() -> new NoSuchElementException("Activity not found"));

        // Step 4: Log retrieved activity information for debugging
        logger.info("Retrieved activity ID: {}", retrievedActivity.getId());
        logger.info("Retrieved activity name: {}", retrievedActivity.getName());

        // Step 5: Assert the results
        assertThat(retrievedActivity, is(equalTo(savedActivity)));
    }

    @Test
    @Transactional
    public void shouldDeleteActivity() {
        // Step 1: Save a new activity
        ActivityDb savedActivity = activityRepository.save(new ActivityDb(TEST_ACTIVITY_NAME));

        // Step 2: Log information for debugging
        logger.info("Saved activity ID: {}", savedActivity.getId());

        // Step 3: Delete the saved activity
        int result = activityRepository.deleteByName(TEST_ACTIVITY_NAME);

        // Step 4: Log the deletion result for debugging
        logger.info("Deletion result: {}", result);

        // Step 5: Attempt to retrieve the deleted activity (should not exist)
        Optional<ActivityDb> fetchedActivity = activityRepository.getByName(TEST_ACTIVITY_NAME);

        // Step 6: Log the attempt to retrieve the deleted activity for debugging
        if (fetchedActivity.isPresent()) {
            logger.info("Fetched activity ID: {}", fetchedActivity.get().getId());
            logger.info("Fetched activity name: {}", fetchedActivity.get().getName());
        } else {
            logger.info("No activity found after deletion.");
        }

        // Step 7: Assert the results
        assertThrows(NoSuchElementException.class, fetchedActivity::get);
        assertThat(result, is(equalTo(1)));
    }
    @Test
    @Transactional
    public void shouldUpdateNameOfActivity() {
        // Step 1: Create the activity with the original name
        ActivityDb oldActivity = activityRepository.save(new ActivityDb(TEST_ACTIVITY_NAME));

        // Step 2: Attempt to update the activity name
        int result = activityRepository.updateActivityName("test2", oldActivity.getId());

        // Step 3: Log information for debugging
        logger.info("Update result: {}", result);
        logger.info("Updated activity ID: {}", oldActivity.getId());

        activityRepository.flush();

        // Step 4: Fetch the activity after the update
        ActivityDb fetchedActivity = activityRepository.findById(oldActivity.getId())
                .orElseThrow(() -> new NoSuchElementException("Activity not found"));

        // Step 5: Log the updated activity's name for debugging
        logger.info("Updated activity name: {}", fetchedActivity.getName());

        // Step 6: Assert the results
        assertThat(result, is(equalTo(1)));
        assertEquals(oldActivity.getId(), fetchedActivity.getId());
        assertEquals("test2", fetchedActivity.getName());
    }
}
