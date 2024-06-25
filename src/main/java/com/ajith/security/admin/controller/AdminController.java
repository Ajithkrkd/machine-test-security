package com.ajith.security.admin.controller;

import com.ajith.security.admin.service.IAdminService;
import com.ajith.security.user.dto.BasicResponse;
import com.ajith.security.user.dto.UserDetailsResponse;
import com.ajith.security.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IAdminService iAdminService;
    @GetMapping("")
    public ResponseEntity<String>getAdmin(){
        return ResponseEntity.ok ( "admin is here " );
    }

    @GetMapping("/users/all")
    public ResponseEntity< List<UserDetailsResponse> > getAllUsers(){
        return iAdminService.getAllUsers();
    }

    public ResponseEntity< BasicResponse > blockUser(@PathVariable ("id") Long userId){

    }
}
