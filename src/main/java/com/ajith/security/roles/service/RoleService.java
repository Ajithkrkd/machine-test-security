package com.ajith.security.roles.service;

import com.ajith.security.admin.dto.RoleRequest;
import com.ajith.security.exceptions.CustomRoleNotFoundException;
import com.ajith.security.exceptions.NoAccessException;
import com.ajith.security.exceptions.RoleAlreadyExistException;
import com.ajith.security.roles.model.Role;
import com.ajith.security.roles.repository.RoleRepository;
import com.ajith.security.user.dto.BasicResponse;
import com.ajith.security.user.model.User;
import com.ajith.security.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    @Override
    public ResponseEntity < BasicResponse > createRole (RoleRequest roleRequest) {
        try {

            String roleName = roleRequest.getRoleName ().toUpperCase (  );
            String prependedRoleName = "ROLE_" + roleName;
            Role newRole = Role.builder ( )
                    .roleName ( prependedRoleName )
                    .build ( );
            Optional<Role> role = roleRepository.findByRoleName ( prependedRoleName );
            if(role.isPresent ()){
                throw new RoleAlreadyExistException (" Role already exists");
            }
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

    @Override
    public ResponseEntity < Role > getRoleById (Integer roleId) {
        try {
            Role role = roleRepository.findById ( roleId )
                    .orElseThrow (()-> new RoleNotFoundException ( "Role " + roleId + " not available") );
            return ResponseEntity.status ( HttpStatus.OK ).body ( role );
        }catch (RoleNotFoundException e) {
            log.info ( e.getMessage () );
            throw new CustomRoleNotFoundException (e.getMessage ());
        }

    }

    @Override
    public ResponseEntity < BasicResponse > deleteRoleById (Integer roleId) {
        try{
            Role role = roleRepository.findById ( roleId )
                    .orElseThrow (()->new RoleNotFoundException ( "Role " + roleId + " not available") );
            if( role.equals ( "ROLE_ADMIN" )){
                throw new NoAccessException ("you don't have permission to access this role");
            }
            List< User > usersList = userRepository.findAll ();
            usersList.stream()
                    .filter(user -> user.getRole().getRoleId () == (roleId))
                    .forEach(user -> {
                        user.setRole(null);
                        userRepository.save(user);
                    });
        roleRepository.delete ( role );
        return  ResponseEntity.status ( HttpStatus.OK ).body (
                BasicResponse.builder ()
                        .message ( "role " +role.getRoleName () + " deleted successfully" )
                        .description ( "you have successfully deleted role and removed assigned roles from user" )
                        .timestamp ( LocalDateTime.now (  ) )
                        .build ());
        }catch (RoleNotFoundException e){
            throw new CustomRoleNotFoundException ( e.getMessage () );
        }
        catch (NoAccessException e){
            log.info ( "trying to access admin role id" );
            throw new NoAccessException ( e.getMessage () );
        }
    }
}
