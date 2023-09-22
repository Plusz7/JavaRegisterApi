package com.myproject.registerApi.repository;

import com.myproject.registerApi.model.ActivityDTO;
import com.myproject.registerApi.model.db.ActivityDb;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

//@Repository
public interface ActivityRepository extends JpaRepository<ActivityDTO, Long> {

    @NonNull
    @Transactional
    Optional<ActivityDb> save(ActivityDb activityDb);
}