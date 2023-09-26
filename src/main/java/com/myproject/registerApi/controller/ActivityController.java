package com.myproject.registerApi.controller;

import com.myproject.registerApi.model.ActivityDTO;
import com.myproject.registerApi.service.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActivityController implements ActivityApi {

    private  final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public ResponseEntity<ActivityDTO> saveActivity(ActivityDTO activityDTO) {
        ActivityDTO activity = activityService.saveActivity(activityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(activity);
    }

    @Override
    public ResponseEntity<ActivityDTO> getActivityByName(String name) {
        ActivityDTO activity = activityService.getActivity(name);
        return ResponseEntity.ok().body(activity);
    }

    @Override
    public ResponseEntity<List<ActivityDTO>> getAllActivities() {
        List<ActivityDTO> activities = activityService.getAllActivities();
        return ResponseEntity.ok().body(activities);
    }

    @Override
    public ResponseEntity<Void> updateActivityByName(String oldName, ActivityDTO activityDTO) {
        activityService.updateActivity(oldName, activityDTO.getName());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteActivityByName(String name) {
        activityService.deleteActivity(name);
        return ResponseEntity.ok().build();
    }
}
