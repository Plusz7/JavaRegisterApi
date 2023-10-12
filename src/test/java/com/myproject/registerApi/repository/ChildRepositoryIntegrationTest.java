package com.myproject.registerApi.repository;

import com.myproject.registerApi.model.db.ChildDb;
import com.myproject.registerApi.model.enums.Gender;
import org.junit.jupiter.api.Test;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureMockMvc
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class ChildRepositoryIntegrationTest {

    private static final ChildDb childDb = new ChildDb(
            "firstname",
            "lastname",
            5,
            Gender.MALE,
            "12342143",
            null
    );

    @Autowired
    private ChildRepository childRepository;

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

        ChildDb savedChild = childRepository.save(childDb);

        childRepository.deleteById(savedChild.getId());

        Optional<ChildDb> fetchedChild = childRepository.findById(savedChild.getId());

        assertThrows(NoSuchElementException.class, fetchedChild::get);
        assertFalse(childRepository.findById(savedChild.getId()).isPresent());
    }

}
