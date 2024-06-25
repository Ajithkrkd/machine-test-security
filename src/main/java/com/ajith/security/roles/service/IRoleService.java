package com.ajith.security.roles.service;

import com.ajith.security.admin.dto.RoleRequest;
import com.ajith.security.roles.model.Role;
import com.ajith.security.user.dto.BasicResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRoleService {

    ResponseEntity< BasicResponse> createRole (RoleRequest roleRequest);

    ResponseEntity< List< Role>> getAllRoles ( );
}
