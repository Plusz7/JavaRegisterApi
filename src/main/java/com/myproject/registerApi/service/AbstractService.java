package com.myproject.registerApi.service;

import com.myproject.registerApi.exception.FailedToExecuteException;
import org.slf4j.Logger;

public abstract class AbstractService {

    Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ActivityService.class);

    public void isSuccess(int status, String name) {
        if (status == 0) {
            throw new FailedToExecuteException("Failed to execute the command for a given entity, name: " + name);
        } else {
            LOGGER.info("The command for a given entity, name: " + name + " is successful");
        }
    }
}
