package com.ajith.security.exceptions;

public class AuthHeaderNotContainExpectedTokenException extends RuntimeException {
    public AuthHeaderNotContainExpectedTokenException (String message) {
        super(message);
    }
}
