package com.ajith.security.user.iservice;

import com.ajith.security.user.dto.BasicResponse;
import com.ajith.security.user.dto.ChangePasswordRequest;
import com.ajith.security.user.dto.UserDetailsResponse;
import com.ajith.security.user.dto.UserUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    boolean isEmailExist (String email);

    ResponseEntity< BasicResponse> changePassword (String authHeader, ChangePasswordRequest changePasswordRequest);
    ResponseEntity< UserDetailsResponse > getUserDetails (Integer userId);
    ResponseEntity< BasicResponse> updateUserDetails (UserUpdateRequest userUpdateRequest, String authHeader);

    ResponseEntity< UserDetailsResponse> getUserDetailsFromAuthHeader (String authHeader);
}
