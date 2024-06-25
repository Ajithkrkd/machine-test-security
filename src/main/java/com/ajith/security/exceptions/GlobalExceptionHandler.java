package com.ajith.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler (value = {EmailAlreadyExistsException.class})
    @ResponseStatus (value = HttpStatus.CONFLICT)
    public ErrorResponse emailAlreadyExist(EmailAlreadyExistsException ex, WebRequest request)
    {
        ErrorResponse message = new ErrorResponse();
        message.setMessage ( ex.getMessage() );
        message.setDescription ( "There is conflict between emails , this email already in use" );
        message.setTimestamp ( LocalDateTime.now ( ) );
        return message;
    }

    @ExceptionHandler (value = {UserBlockedException.class})
    @ResponseStatus (value = HttpStatus.NOT_FOUND)
    public ErrorResponse userBlockedException(UserBlockedException ex, WebRequest request)
    {
        ErrorResponse message = new ErrorResponse();
        message.setMessage ( ex.getMessage() );
        message.setDescription ( "user is blocked try to connect with support" );
        message.setTimestamp ( LocalDateTime.now ( ) );
        return message;
    }
    @ExceptionHandler (value = {CustomBadCredentialException.class})
    @ResponseStatus (value = HttpStatus.UNAUTHORIZED)
    public ErrorResponse customBadCredentialException(CustomBadCredentialException ex, WebRequest request)
    {
        ErrorResponse message = new ErrorResponse();
        message.setMessage ( ex.getMessage() );
        message.setDescription ( "user name or password is incorrect" );
        message.setTimestamp ( LocalDateTime.now ( ) );
        return message;
    }
    @ExceptionHandler (value = {CustomTokenExpirationException.class})
    @ResponseStatus (value = HttpStatus.UNAUTHORIZED)
    public ErrorResponse customTokenExpirationException(CustomTokenExpirationException ex, WebRequest request)
    {
        ErrorResponse message = new ErrorResponse();
        message.setMessage ( ex.getMessage() );
        message.setDescription ("your token is expired");
        message.setTimestamp ( LocalDateTime.now ( ) );
        return message;
    }

    @ExceptionHandler(value = {AuthHeaderNotContainExpectedTokenException.class})
    @ResponseStatus (value = HttpStatus.UNAUTHORIZED)
    public ErrorResponse authHeaderNotContainExpectedTokenException(
            AuthHeaderNotContainExpectedTokenException ex, WebRequest request)
    {
        ErrorResponse message = new ErrorResponse();
        message.setMessage ( ex.getMessage() );
        message.setDescription ("your Authorization header is not valid");
        message.setTimestamp ( LocalDateTime.now ( ) );
        return message;
    }

    @ExceptionHandler(value = {MissingUserAddressException.class})
    @ResponseStatus (value = HttpStatus.OK)
    public ErrorResponse missingUserAddress(
            MissingUserAddressException ex, WebRequest request)
    {
        ErrorResponse message = new ErrorResponse();
        message.setMessage ( ex.getMessage() );
        message.setDescription ("user does not added his address he need add a address first");
        message.setTimestamp ( LocalDateTime.now ( ) );
        return message;
    }

}
