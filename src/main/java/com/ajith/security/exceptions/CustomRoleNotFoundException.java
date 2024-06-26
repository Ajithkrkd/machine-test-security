package com.ajith.security.exceptions;

public class CustomRoleNotFoundException extends RuntimeException {
    public CustomRoleNotFoundException (String message) {
        super(message);
    }
}
