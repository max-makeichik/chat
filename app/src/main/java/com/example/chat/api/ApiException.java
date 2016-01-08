package com.example.chat.api;

public class ApiException extends RuntimeException {
    private final String errorMessage;

    public ApiException(String errorMessage) {
        super(errorMessage);
        //this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
