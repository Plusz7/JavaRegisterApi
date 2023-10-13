package com.myproject.registerApi.service;

import com.myproject.registerApi.model.ActivityDTO;

import java.util.List;
import java.util.UUID;

public interface ActivityService {

    ActivityDTO saveActivity(ActivityDTO activityDTO);
    ActivityDTO getActivityByName(String name);
    List<ActivityDTO> getAllActivities();
    void updateActivity(String oldName, String newName);
    void deleteActivity(String name);
}
