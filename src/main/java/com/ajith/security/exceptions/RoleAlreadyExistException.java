package com.ajith.security.exceptions;

public class RoleAlreadyExistException extends RuntimeException {
    public RoleAlreadyExistException (String message) {
    super(message);
    }
}
