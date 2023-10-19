package com.myproject.registerApi.repository;

import com.myproject.registerApi.model.db.TeacherDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherDb, UUID> {

    Optional<TeacherDb> findByEmail(String email);
}
