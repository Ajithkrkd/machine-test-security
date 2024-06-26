package com.ajith.security.user.service;

import com.ajith.security.config.JwtServiceImpl;
import com.ajith.security.exceptions.UserNotFoundException;
import com.ajith.security.user.dto.BasicResponse;
import com.ajith.security.user.dto.ChangePasswordRequest;
import com.ajith.security.user.dto.UserDetailsResponse;
import com.ajith.security.user.dto.UserUpdateRequest;
import com.ajith.security.user.iservice.IUserService;
import com.ajith.security.user.model.User;
import com.ajith.security.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final JwtServiceImpl jwtService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public boolean isEmailExist (String email) {
        return userRepository.existsByEmail (email);
    }

    @Override
    public ResponseEntity<BasicResponse> changePassword(String authHeader, ChangePasswordRequest changePasswordRequest) {
        User validUser = jwtService.extractUserFromAuthHeader (authHeader);

        if (passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), validUser.getPassword())) {
            validUser.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(validUser);
            return ResponseEntity.status ( HttpStatus.OK ).body ( BasicResponse.builder()
                            .message (   "Password changed successfully " )
                            .timestamp ( LocalDateTime.now () )
                            .description ( "you have changed your password to " + changePasswordRequest.getNewPassword ())
                    .build() );
        } else {
            return ResponseEntity.status ( HttpStatus.BAD_REQUEST ).body ( BasicResponse.builder ( )
                    .message ( "Your current password is incorrect" )
                    .description ( "Please check your current password and try again" )
                    .build ( ) );
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

    @Override
    public ResponseEntity < BasicResponse > updateUserDetails (UserUpdateRequest userUpdateRequest, String authHeader) {

        User validUser = jwtService.extractUserFromAuthHeader (authHeader);
        validUser.setFullName(userUpdateRequest.getFullName() != null ? userUpdateRequest.getFullName() : validUser.getFullName());
        validUser.setPhoneNumber(userUpdateRequest.getPhoneNumber() != null ? userUpdateRequest.getPhoneNumber() : validUser.getPhoneNumber());
        userRepository.save ( validUser );
        return ResponseEntity.status ( HttpStatus.OK ).body ( BasicResponse.builder()
                .message (   "user updated successfully " )
                .description ( "you have changed your details to " + userUpdateRequest.getFullName () + " and" + userUpdateRequest.getPhoneNumber ())
                .build() );
    }

    @Override
    public ResponseEntity < UserDetailsResponse > getUserDetailsFromAuthHeader (String authHeader) {
       try{
           User validUser = jwtService.extractUserFromAuthHeader (authHeader);
           if(validUser != null){
               UserDetailsResponse response = mapUserToUserDetails ( validUser );
               return ResponseEntity.status ( HttpStatus.OK ).body ( response );
           }else{
               throw new UserNotFoundException("user not found");
           }

       }catch (UserNotFoundException e){
           throw new UserNotFoundException(e.getMessage ());
       }
    }
}
