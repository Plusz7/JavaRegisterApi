package com.myproject.registerApi.config;

import com.myproject.registerApi.model.Error;
import com.myproject.registerApi.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
 *  @author: DevProblems(Sarang Kumar A Tak)
 *  Youtube channel: https://youtube.com/@devproblems
 */
@ControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> userNotFoundException(final NotFoundException e) {
        return ResponseEntity.ok(getError(e.getMessage(), HttpStatus.NOT_FOUND.value()));
    }


    private Error getError(String message, Integer code) {
        Error error = new Error();
        error.setMessage(message);
        error.setCode(code);
        return error;
    }
}
