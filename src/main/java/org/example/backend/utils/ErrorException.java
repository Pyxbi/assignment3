package org.example.backend.utils;

public class ErrorException extends Exception{
    private final int errorCode;

    public ErrorException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
