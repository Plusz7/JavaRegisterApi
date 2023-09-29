package com.myproject.registerApi.validation;

import com.myproject.registerApi.service.ActivityServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class ActivityValidator {

    private final ActivityServiceImpl activityService;

    public ActivityValidator(ActivityServiceImpl activityService) {
        this.activityService = activityService;
    }


}
