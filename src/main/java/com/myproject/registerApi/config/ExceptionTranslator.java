package com.myproject.registerApi.config;

import com.myproject.registerApi.exception.AlreadyExistsException;
import com.myproject.registerApi.exception.FailedToUpdateException;
import com.myproject.registerApi.model.Error;
import com.myproject.registerApi.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> notFoundException(final NotFoundException e) {
        return ResponseEntity.ok(getError(e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Error> alreadyExistsException(AlreadyExistsException e) {
        return ResponseEntity.ok(getError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(FailedToUpdateException.class)
    public ResponseEntity<Error> failedToUpdateException(FailedToUpdateException e) {
        return ResponseEntity.ok(getError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler
    public ResponseEntity<Error> defaultException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }


    private Error getError(String message, Integer code) {
        Error error = new Error();
        error.setMessage(message);
        error.setCode(code);
        return error;
    }
}
