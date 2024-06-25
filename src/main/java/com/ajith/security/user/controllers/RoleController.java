package com.ajith.security.user.controllers;

import com.ajith.security.user.dto.BasicResponse;
import com.ajith.security.user.dto.RoleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/roles")
public class RoleController {

    @GetMapping("/create")
    public ResponseEntity< BasicResponse > createRole(@RequestBody RoleRequest roleRequest){
        return null;
    }
}
