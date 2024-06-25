package com.ajith.security.roles.service;

import com.ajith.security.admin.dto.RoleRequest;
import com.ajith.security.roles.model.Role;
import com.ajith.security.roles.repository.RoleRepository;
import com.ajith.security.user.dto.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;
    @Override
    public ResponseEntity < BasicResponse > createRole (RoleRequest roleRequest) {
        try {
            String roleName = roleRequest.getRoleName ().toUpperCase (  );
            String prependedRoleName = "ROLE_" + roleName;
            Role newRole = Role.builder ( )
                    .roleName ( prependedRoleName )
                    .build ( );
            roleRepository.save ( newRole );
            return ResponseEntity.status ( HttpStatus.CREATED ).body (
                    BasicResponse.builder()
                            .message ( "you have created new Role  : " + newRole.getRoleName () )
                            .description ( "role created successfully" )
                            .timestamp ( LocalDateTime.now () )
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException ( e );
        }
    }

    @Override
    public ResponseEntity < List < Role > > getAllRoles ( ) {
        return ResponseEntity.status ( HttpStatus.OK ).body ( roleRepository.findAll ());
    }
}
