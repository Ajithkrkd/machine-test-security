package com.ajith.security.exceptions;

public class CustomTokenExpirationException extends RuntimeException {
    public CustomTokenExpirationException (String message) {
        super(message);
    }
}
