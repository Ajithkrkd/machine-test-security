package com.ajith.security.roles.service;

import com.ajith.security.admin.dto.RoleRequest;
import com.ajith.security.exceptions.CustomRoleNotFoundException;
import com.ajith.security.exceptions.NoAccessException;
import com.ajith.security.exceptions.RoleAlreadyExistException;
import com.ajith.security.exceptions.UserNotFoundException;
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
    public ResponseEntity<BasicResponse> deleteRoleById(Integer roleId) {
        try {
            System.out.println ("entered to this " );
            Role role = roleRepository.findById ( roleId )
                    .orElseThrow (()-> new RoleNotFoundException ( "Role " + roleId + " not available") );

            System.out.println (role.getRoleName () +"--------------------------------role");
            if ("ROLE_ADMIN".equals(role.getRoleName())) {
                System.out.println (role +"--------------------------------role");
                throw new NoAccessException("You don't have permission to access this role");
            }

            List<User> usersList = userRepository.findAll();
            usersList.stream()
                    .filter(user -> user.getRole() != null && user.getRole().getRoleId() ==(roleId))
                    .forEach(user -> {
                        user.setRole(null);
                        userRepository.save(user);
                    });

            roleRepository.delete(role);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(BasicResponse.builder()
                            .message("Role " + role.getRoleName() + " deleted successfully")
                            .description("Successfully deleted role and removed assigned roles from users")
                            .timestamp(LocalDateTime.now())
                            .build());
        } catch (RoleNotFoundException e) {
        System.out.println ("role not found catch block" );
            throw new CustomRoleNotFoundException(e.getMessage());
        } catch (NoAccessException e) {
            log.info("Attempted to delete admin role");
            throw new NoAccessException(e.getMessage());
        }
    }


    @Override
    public ResponseEntity < BasicResponse > assignRoleById (Integer userId, Integer roleId) {
        try{
            Role role = roleRepository.findById ( roleId )
                    .orElseThrow (()->new CustomRoleNotFoundException ( "Role " + roleId + " not available") );
            User user = userRepository.findById ( userId ).orElseThrow (
                    ()->new UserNotFoundException ( "user does not exist can't assign role" )
            );
            user.setRole ( role );
            userRepository.save ( user );
            return ResponseEntity.status ( HttpStatus.OK ).body ( BasicResponse.builder ()
                    .message ( "user role assigned as " + role.getRoleName () )
                    .description ( "user assigned new role " )
                    .timestamp ( LocalDateTime.now (  ) )
                    .build ());

        }catch (CustomRoleNotFoundException e){
            throw new CustomRoleNotFoundException ( e.getMessage () );
        }catch (UserNotFoundException e){
            throw new UserNotFoundException ( e.getMessage () );
        }
    }
}
