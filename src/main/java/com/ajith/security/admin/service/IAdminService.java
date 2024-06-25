package com.ajith.security.admin.service;

import com.ajith.security.user.dto.BasicResponse;
import com.ajith.security.user.dto.UserDetailsResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAdminService {
    ResponseEntity< List< UserDetailsResponse>> getAllUsers ( );

    ResponseEntity< BasicResponse> toggleUserblockStatus (Integer userId);

    ResponseEntity< UserDetailsResponse> getUserDetails (Integer userId);
}
