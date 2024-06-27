package com.ajith.security.exceptions;

import io.jsonwebtoken.JwtException;

import java.security.SignatureException;

public class JwtTokenInvalidException extends JwtException {
    public  JwtTokenInvalidException(String message){
        super(message);
    }
}
