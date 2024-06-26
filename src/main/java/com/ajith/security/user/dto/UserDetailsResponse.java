package com.ajith.security.user.dto;

import com.ajith.security.roles.model.Role;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@ToString
public class UserDetailsResponse {
    private int userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private boolean isBlocked ;
    private boolean isActive ;
    private Role role;
}
