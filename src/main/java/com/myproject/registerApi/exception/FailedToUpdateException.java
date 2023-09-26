package com.myproject.registerApi.exception;

public class FailedToUpdateException extends RuntimeException {
    public FailedToUpdateException(String message) {
        super(message);
    }
}
