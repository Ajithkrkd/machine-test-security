package com.ajith.security.user.controllers;

import com.ajith.security.user.dto.BasicResponse;
import com.ajith.security.user.dto.ChangePasswordRequest;
import com.ajith.security.user.dto.UserDetailsResponse;
import com.ajith.security.user.dto.UserUpdateRequest;
import com.ajith.security.user.iservice.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService iUserService;

    @PostMapping("/change_password")
    public ResponseEntity < BasicResponse > changePassword(
            @RequestHeader ("Authorization") String authHeader,
            @RequestBody ChangePasswordRequest changePasswordRequest){
        return iUserService.changePassword(authHeader,changePasswordRequest);
    }
    @PostMapping("/update")
    public ResponseEntity< BasicResponse > updateUserDetails(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody UserUpdateRequest userUpdateRequest)
    {
        return iUserService.updateUserDetails(userUpdateRequest,authHeader);
    }

    @GetMapping("/details")
    public ResponseEntity< UserDetailsResponse > getUserDetails(
            @RequestHeader("Authorization") String authHeader
    )
    {
        return iUserService.getUserDetailsFromAuthHeader(authHeader);
    }
}
