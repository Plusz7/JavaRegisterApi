package com.myproject.registerApi.exception;

public class FailedToExecuteException extends RuntimeException {
    public FailedToExecuteException(String message) {
        super(message);
    }
}
