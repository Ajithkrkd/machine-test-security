package com.ajith.security.user.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequest {
    private String email;
    private String password;
}