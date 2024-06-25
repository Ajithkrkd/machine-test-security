package com.ajith.security.exceptions;


public class CustomBadCredentialException extends RuntimeException {
    public CustomBadCredentialException (String message) {
        super(message);
    }
}