package com.ajith.security.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {
    private String roleName;
    private String roleDescription;
}
