package com.myproject.registerApi.repository;

import com.myproject.registerApi.model.db.ChildDb;
import com.myproject.registerApi.model.enums.Gender;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChildRepository extends JpaRepository<ChildDb, UUID> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE ChildDb c SET c.firstName = :firstName, c.lastName = :lastName, c.age = :age, c.gender = :gender, c.socialNumber = :socialNumber WHERE c.id = :id")
    int updateChildInfo(
            @Param("id") UUID id,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("age") Integer age,
            @Param("gender") Gender gender,
            @Param("socialNumber") String socialNumber
    );

//    @Modifying
//    @Query(value = "DELETE FROM ChildDb f WHERE f.id = :id")
//    int customDeleteById(@Param("id") UUID id);

    void deleteById(UUID id);
}
