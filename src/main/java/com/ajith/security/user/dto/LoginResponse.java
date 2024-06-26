package com.ajith.security.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginResponse {
    private String username;
    private String access_token;
    private boolean isLogged;
}