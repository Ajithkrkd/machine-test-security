package com.ajith.security.auth;

import com.ajith.security.auth.AuthenticationService;
import com.ajith.security.user.dto.BasicResponse;
import com.ajith.security.user.dto.LoginRequest;
import com.ajith.security.user.dto.LoginResponse;
import com.ajith.security.user.dto.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping ("/register")
    public ResponseEntity < BasicResponse > registerUser(@RequestBody RegistrationRequest request){
        ResponseEntity<BasicResponse> response = authenticationService.register(request);
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity < LoginResponse > login(@RequestBody LoginRequest request){
        ResponseEntity<LoginResponse> response  = authenticationService.login(request);
        return response;
    }
}
