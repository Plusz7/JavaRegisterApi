package com.myproject.registerApi.service;

import com.myproject.registerApi.model.ActivityDTO;

import java.util.List;

public interface ActivityService {

    public ActivityDTO saveActivity(ActivityDTO activityDTO);
    public ActivityDTO getActivity(String name);
    public List<ActivityDTO> getAllActivities();
    public void updateActivity(String oldName, String newName);
    public void deleteActivity(String name);
}
