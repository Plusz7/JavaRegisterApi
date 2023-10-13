package com.myproject.registerApi.repository;

import com.myproject.registerApi.model.db.TeacherDb;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureMockMvc
@AutoConfigureDataJpa
@ActiveProfiles("test")
@SpringBootTest
public class TeacherRepositoryIntegrationTest {

    private final TeacherRepository teacherRepository;

    private final TeacherDb testTeacher = new TeacherDb(
            null,
            "firstName",
            "lastName",
            "some@mail.com"
    );

    @Autowired
    public TeacherRepositoryIntegrationTest(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Test
    @Transactional
    public void shouldSaveAndRetrieveTeacher() {

        TeacherDb savedTeacher = teacherRepository.save(testTeacher);

        TeacherDb foundTeacher = teacherRepository.findById(savedTeacher.getId())
                .orElseThrow(() -> new NoSuchElementException("Activity not found"));

        assertEquals(foundTeacher , savedTeacher);
    }

    @Test
    @Transactional
    public void shouldDeleteTeacher() {
        TeacherDb savedTeacher = teacherRepository.save(testTeacher);

        teacherRepository.deleteById(savedTeacher.getId());

        Optional<TeacherDb> fetchedTeacher = teacherRepository.findById(savedTeacher.getId());

        assertThrows(NoSuchElementException.class, fetchedTeacher::get);
    }


}
