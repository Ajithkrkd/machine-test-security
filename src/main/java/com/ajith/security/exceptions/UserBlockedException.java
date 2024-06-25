package com.ajith.security.exceptions;


public class UserBlockedException extends RuntimeException {
    public UserBlockedException(String message) {
        super(message);
    }
}