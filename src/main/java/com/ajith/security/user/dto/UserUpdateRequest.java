package com.ajith.security.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserUpdateRequest {
    private String fullName;
    private String phoneNumber;

}