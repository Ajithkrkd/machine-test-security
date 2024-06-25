package com.ajith.security.user.dto;

import com.ajith.security.user.model.Role;

public class UserDetailsResponse {
    private int userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private boolean isBlocked ;
    private boolean isActive ;
    private Role role;
}
