package com.ajith.security.admin.controller;

import com.ajith.security.admin.service.IAdminService;
import com.ajith.security.user.dto.BasicResponse;
import com.ajith.security.user.dto.UserDetailsResponse;
import com.ajith.security.user.iservice.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IAdminService iAdminService;
    private final IUserService iUserService;

    @GetMapping("/user/all")
    public ResponseEntity< List<UserDetailsResponse> > getAllUsers(){
        return iAdminService.getAllUsers();
    }

    @PostMapping("/user/block/{id}")
    public ResponseEntity< BasicResponse > toggleUserBlockStatus(@PathVariable ("id") Integer userId){
        return iAdminService.toggleUserBlockStatus (userId);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity< UserDetailsResponse > getUserDetails(@PathVariable ("id") Integer userId)
    {
        return iUserService.getUserDetails (userId);
    }

}
