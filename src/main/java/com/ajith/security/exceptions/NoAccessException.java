package com.ajith.security.exceptions;

public class NoAccessException extends RuntimeException {
    public NoAccessException (String message) {
        super(message);
    }
}
