package com.ajith.security.admin.service;

import com.ajith.security.exceptions.UserNotFoundException;
import com.ajith.security.roles.model.Role;
import com.ajith.security.roles.repository.RoleRepository;
import com.ajith.security.user.dto.BasicResponse;
import com.ajith.security.user.dto.UserDetailsResponse;
import com.ajith.security.user.model.User;
import com.ajith.security.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService implements IAdminService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity < List < UserDetailsResponse > > getAllUsers ( ) {
        try {
            List< User > users  = userRepository.findAll ();
            List<UserDetailsResponse> userDetailsList = users.stream()
                    .map(this::mapUserToUserDetails).toList ();
            return ResponseEntity.status ( HttpStatus.OK ).body ( userDetailsList );
        }catch (Exception e) {
            throw new RuntimeException ( e.getMessage () );
        }
    }

    @Override
    public ResponseEntity < BasicResponse > toggleUserBlockStatus (Integer userId) {
        try {
          User user = userRepository.findById ( userId )
                  .orElseThrow (()->new UserNotFoundException ( "user does not exist with this id  "+userId ) );

          user.setBlocked ( !user.isBlocked () );
          userRepository.save ( user );
          return ResponseEntity.status ( HttpStatus.OK ).body ( BasicResponse.builder()
                          .message ( user.isBlocked () ? "user blocked successfully" : "user  unblocked successfully" )
                          .description ( "you have changed user status" )
                          .timestamp ( LocalDateTime.now () )
                  .build() );
        }catch (UserNotFoundException e) {
            log.error ( e.getMessage () );
            throw new UserNotFoundException ( e.getMessage () );
        }
    }



    @Override
    public void createAdmin ( ) {

            User admin = User.builder ( )
                    .fullName ( "adminUser" )
                    .email ( "admin@gmail.com" )
                    .password ( passwordEncoder.encode ( "ajith" ) )
                    .role ( getAdminRole () )
                    .phoneNumber ( "9087988798" )
                    .build ( );
            userRepository.save ( admin );

    }

    private Role getAdminRole ( ) {
        Optional<Role> adminRole = roleRepository.findByRoleName("ROLE_ADMIN");

        if (adminRole.isPresent()) {
            return adminRole.get();
        } else {
            Role newRole = new Role();
            newRole.setRoleName("ROLE_ADMIN");
            return roleRepository.save(newRole);
        }
    }

    public UserDetailsResponse mapUserToUserDetails(User user) {
        return new UserDetailsResponse(
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.isBlocked(),
                user.isActive(),
                user.getRole()
        );
    }

}
