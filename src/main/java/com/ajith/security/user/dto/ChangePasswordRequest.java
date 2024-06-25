package com.ajith.security.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;
}