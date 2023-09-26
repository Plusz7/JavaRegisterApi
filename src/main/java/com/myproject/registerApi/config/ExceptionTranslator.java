package com.myproject.registerApi.config;

import com.myproject.registerApi.exception.AlreadyExistsException;
import com.myproject.registerApi.exception.FailedToExecuteException;
import com.myproject.registerApi.model.Error;
import com.myproject.registerApi.exception.NotFoundException;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionTranslator {

    Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ExceptionTranslator.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> notFoundException(final NotFoundException e) {
        LOGGER.atError().setCause(e).log("Exception: {} not found", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getError(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Error> alreadyExistsException(AlreadyExistsException e) {
        LOGGER.atError().setCause(e).log("Exception: {} already exists: ", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getError(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(FailedToExecuteException.class)
    public ResponseEntity<Error> failedToExecuteException(FailedToExecuteException e) {
        LOGGER.atError().setCause(e).log("Exception: {} failed to execute", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler
    public ResponseEntity<Error> defaultException(Exception e) {
        LOGGER.atError().setCause(e).log("Exception: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(getError(e.getMessage(), HttpStatus.NOT_IMPLEMENTED.value()));
    }


    private Error getError(String message, Integer code) {
        Error error = new Error();
        error.setMessage(message);
        error.setCode(code);
        return error;
    }
}
