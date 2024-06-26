package com.ajith.security.roles.controller;

import com.ajith.security.roles.model.Role;
import com.ajith.security.roles.service.IRoleService;
import com.ajith.security.user.dto.BasicResponse;
import com.ajith.security.admin.dto.RoleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService iRoleService;
    @PostMapping ("/create")
    public ResponseEntity< BasicResponse > createRole(@RequestBody RoleRequest roleRequest){
        return iRoleService.createRole(roleRequest);
    }
    @GetMapping("/getAll")
    public ResponseEntity< List < Role > > getAllRoles(){
        return iRoleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable ("id") Integer roleId){
        return iRoleService.getRoleById(roleId);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<BasicResponse> deleteRole(@PathVariable ("id") Integer roleId){
        return iRoleService.deleteRoleById(roleId);
    }

    @PostMapping("/assignRole/{userId}/{roleId}")
    public ResponseEntity<BasicResponse> assignRole(@PathVariable ("userId") Integer userId, @PathVariable ("roleId") Integer roleId){
        return iRoleService.assignRoleById(userId, roleId);
    }

}
