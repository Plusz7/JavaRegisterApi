package com.myproject.registerApi.repository;

import com.myproject.registerApi.model.db.ActivityDb;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface ActivityRepository extends JpaRepository<ActivityDb, UUID> {

    Optional<ActivityDb> getByName(String name);
    @Modifying
    @Transactional
    @Query("UPDATE ActivityDb a SET a.name = :name WHERE a.id = :id")
    int updateActivityByName(@Param("name") String name, @Param("id") UUID id);
    int deleteByName(String name);
}