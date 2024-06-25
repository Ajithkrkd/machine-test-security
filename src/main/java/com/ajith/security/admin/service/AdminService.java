package com.ajith.security.admin.service;

import com.ajith.security.exceptions.UserNotFoundException;
import com.ajith.security.user.dto.BasicResponse;
import com.ajith.security.user.dto.UserDetailsResponse;
import com.ajith.security.user.model.User;
import com.ajith.security.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity < BasicResponse > toggleUserblockStatus (Integer userId) {
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
    public ResponseEntity < UserDetailsResponse > getUserDetails (Integer userId) {
        try {
            User user = userRepository.findById ( userId )
                    .orElseThrow (()->new UserNotFoundException ( "user does not exist with this id  "+userId ) );
            return ResponseEntity.status ( HttpStatus.OK ).body ( mapUserToUserDetails ( user ) );

        }catch (UserNotFoundException e) {
            log.error ( e.getMessage () );
            throw new UserNotFoundException ( e.getMessage () );
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
