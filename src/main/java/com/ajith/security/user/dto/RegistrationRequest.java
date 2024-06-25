package com.ajith.security.user.dto;

import lombok.*;


@Data
public class RegistrationRequest {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
}
