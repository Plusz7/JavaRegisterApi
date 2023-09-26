package com.myproject.registerApi.validation;

import com.myproject.registerApi.service.ActivityService;
import org.springframework.stereotype.Component;

@Component
public class ActivityValidator {

    private final ActivityService activityService;

    public ActivityValidator(ActivityService activityService) {
        this.activityService = activityService;
    }


}
