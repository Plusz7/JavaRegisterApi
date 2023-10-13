package com.myproject.registerApi.service;

import com.myproject.registerApi.exception.FailedToExecuteException;
import org.slf4j.Logger;

public abstract class AbstractService {

    Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ActivityServiceImpl.class);

    public void isSuccess(int status, String id) {
        if (status == 0) {
            throw new FailedToExecuteException("Failed to execute the command for a given entity, id: " + id);
        } else {
            LOGGER.info("The command for a given entity, id: " + id + " is successful");
        }
    }
}
