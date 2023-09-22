package com.myproject.registerApi.service;

import com.myproject.registerApi.model.ActivityDTO;
import com.myproject.registerApi.model.db.ActivityDb;
import com.myproject.registerApi.repository.ActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ActivityService {

    public final ActivityRepository activityRepository;


//    public ActivityDTO save (ActivityDTO activityDTO) {
//
//
//
//        Optional<ActivityDb> activityDbOptional = Optional.ofNullable(activityRepository.save(
//                new ActivityDb(
//                        activityDTO.getId().toString(),
//                        activityDTO.getName()
//                )
//        ).orElseThrow(() -> new RuntimeException("Activity not saved")));
//
//        ActivityDb activityDb;
//
//        if(activityDbOptional.isPresent()) {
//
//            activityDb = activityDbOptional.get();
//            return new ActivityDTO();
//
//        }
//    }
}
