package com.myproject.registerApi.service;

import com.myproject.registerApi.exception.AlreadyExistsException;
import com.myproject.registerApi.exception.NotFoundException;
import com.myproject.registerApi.model.ActivityDTO;
import com.myproject.registerApi.model.db.ActivityDb;
import com.myproject.registerApi.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActivityServiceImpl extends AbstractService implements ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }


    public ActivityDTO saveActivity(ActivityDTO activityDTO) {

        ActivityDb activityDb = new ActivityDb(activityDTO.getName().toLowerCase());
        try {

            LOGGER.info("Saving activity: " + activityDb.getName());

            activityDb = activityRepository.save(activityDb);

            LOGGER.info("Activity saved: " + activityDb.getName());

        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
            throw new AlreadyExistsException(activityDTO.getName());
        }

        return new ActivityDTO()
                .id(activityDb.getId())
                .name(activityDb.getName()
                );
    }

    public ActivityDTO getActivityByName(String name) {
        String validName = name.toLowerCase();

        LOGGER.info("Getting activity: " + validName);
        Optional<ActivityDb> optionalActivityDb = activityRepository.findByName(validName.toLowerCase());
        optionalActivityDb.orElseThrow(() -> new NotFoundException("Activity not found: " + validName));

        ActivityDb activityDb = optionalActivityDb.get();
        LOGGER.info("Activity found: " + activityDb.getName());

        return new ActivityDTO()
                .id(activityDb.getId())
                .name(activityDb.getName()
                );
    }

    public List<ActivityDTO> getAllActivities() {
        LOGGER.info("Getting all activities");
        return activityRepository.findAll().stream()
                .map(activityDb -> new ActivityDTO()
                        .id(activityDb.getId())
                        .name(activityDb.getName()
                        ))
                .toList();
    }

    public void updateActivity(String oldName, String newName) {

        String validOldName = oldName.toLowerCase();
        String validNewName = newName.toLowerCase();

        LOGGER.info("Validating names for activity.");

        if (validNewName.equals(validOldName)) {
            throw new AlreadyExistsException(validNewName);
        }
        LOGGER.info("Validation successful.");
        LOGGER.info("Updating activity: " + validOldName + " -> " + validNewName);

        ActivityDTO activityToUpdate = getActivityByName(validOldName);
        isSuccess(
                activityRepository.updateActivityName(validNewName, activityToUpdate.getId()),
                validNewName
        );
    }

    public void deleteActivity(String name) {
        String validName = name.toLowerCase();
        LOGGER.info("Deleting activity: " + validName);
        isSuccess(
                activityRepository.deleteByName(validName),
                validName
        );
        LOGGER.info("Activity deleted: " + validName);
    }
}
