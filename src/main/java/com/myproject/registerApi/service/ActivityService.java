package com.myproject.registerApi.service;

import com.myproject.registerApi.exception.AlreadyExistsException;
import com.myproject.registerApi.exception.NotFoundException;
import com.myproject.registerApi.model.ActivityDTO;
import com.myproject.registerApi.model.db.ActivityDb;
import com.myproject.registerApi.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }


    public ActivityDTO saveActivity(ActivityDTO activityDTO) {

        ActivityDb activityDb = new ActivityDb(activityDTO.getName());
        try {
            activityDb = activityRepository.save(activityDb);
        } catch (Exception e) {
            throw new AlreadyExistsException(
                    "Activity name already exists: " + activityDTO.getName() + "\n \n" + e.getMessage()
            ); // the message is for me, information provided from message are to exposed
        }

        return new ActivityDTO()
                .id(activityDb.getId())
                .name(activityDb.getName()
                );
    }

    public ActivityDTO getActivity(String name) {

        Optional<ActivityDb> optionalActivityDb = activityRepository.getByName(name);
        optionalActivityDb.orElseThrow(() -> new NotFoundException("Activity not found: " + name));
        ActivityDb activityDb = optionalActivityDb.get();

        return new ActivityDTO()
                .id(activityDb.getId())
                .name(activityDb.getName()
                );
    }

    public List<ActivityDTO> getAllActivities() {
        return activityRepository.findAll().stream()
                .map(activityDb -> new ActivityDTO()
                        .id(activityDb.getId())
                        .name(activityDb.getName()
                        ))
                .toList();
    }

    public void updateActivity(String oldName, String newName) {

        ActivityDTO activityDTOToUpdate = getActivity(oldName);
        int isSuccess = activityRepository.updateActivityByName(newName, activityDTOToUpdate.getId());
        if (isSuccess == 0) {
            throw new NotFoundException("Activity not found: " + oldName);
        }
    }

    public void deleteActivity(String name) {
        activityRepository.deleteByName(name);
    }

}
