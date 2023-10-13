package com.myproject.registerApi.repository;

import com.myproject.registerApi.model.db.ChildDb;
import com.myproject.registerApi.model.enums.Gender;
import com.myproject.registerApi.utilities.ActivityTestObjects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static com.myproject.registerApi.utilities.ChildTestObjects.childDb;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@AutoConfigureDataJpa
@ActiveProfiles("test")
@SpringBootTest
public class ChildRepositoryIntegrationTest {

    @Autowired
    private ChildRepository childRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    @Transactional
    public void shouldSaveAndRetrieveChild() {

        ChildDb result = childRepository.save(childDb);

        ChildDb retrievedChild = childRepository.findById(result.getId())
                .orElseThrow(() -> new NoSuchElementException("Child Not Found"));

        assertThat(retrievedChild, is(equalTo(result)));
    }

    @Test
    public void shouldDeleteChild() {

        activityRepository.save(ActivityTestObjects.testActivity1);
        activityRepository.save(ActivityTestObjects.testActivity2);

        ChildDb savedChild = childRepository.save(childDb);

        childRepository.deleteById(savedChild.getId());

        Optional<ChildDb> fetchedChild = childRepository.findById(savedChild.getId());

        assertThrows(NoSuchElementException.class, fetchedChild::get);
        assertFalse(childRepository.findById(savedChild.getId()).isPresent());
    }

    @Test
    public void shouldUpdateChild() {

        activityRepository.save(ActivityTestObjects.testActivity1);
        activityRepository.save(ActivityTestObjects.testActivity2);

        ChildDb oldChild = childRepository.save(childDb);

        int result = childRepository.updateChildInfo(
                oldChild.getId(),
                oldChild.getFirstName(),
                "lastname",
                oldChild.getAge(),
                oldChild.getGender(),
                oldChild.getSocialNumber()
        );

        ChildDb newChild = childRepository.findById(oldChild.getId())
                .orElseThrow(() -> new NoSuchElementException("Child Not Found"));


        assertThat(result, is(equalTo(1)));
        assertEquals("lastname", newChild.getLastName());
    }

}
