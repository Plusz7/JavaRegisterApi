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
public class ActivityServiceImpl extends AbstractService implements ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }


    public ActivityDTO saveActivity(ActivityDTO activityDTO) {

        ActivityDb activityDb = new ActivityDb(activityDTO.getName());
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

    public ActivityDTO getActivity(String name) {

        LOGGER.info("Getting activity: " + name);
        Optional<ActivityDb> optionalActivityDb = activityRepository.getByName(name);
        optionalActivityDb.orElseThrow(() -> new NotFoundException("Activity not found: " + name));

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

        LOGGER.info("Validating names for activity.");

        if (newName.equals(oldName)) {
            throw new AlreadyExistsException(newName);
        }
        LOGGER.info("Validation successful.");
        LOGGER.info("Updating activity: " + oldName + " -> " + newName);

        ActivityDTO activityToUpdate = getActivity(oldName);
        isSuccess(
                activityRepository.updateActivityName(newName, activityToUpdate.getId()),
                newName
        );
    }

    public void deleteActivity(String name) {
        LOGGER.info("Deleting activity: " + name);
        isSuccess(
                activityRepository.deleteByName(name),
                name
        );
        LOGGER.info("Activity deleted: " + name);
    }
}
