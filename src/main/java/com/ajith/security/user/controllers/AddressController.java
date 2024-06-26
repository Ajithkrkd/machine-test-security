package com.ajith.security.user.controllers;

import com.ajith.security.user.dto.AddressRequest;
import com.ajith.security.user.dto.BasicResponse;
import com.ajith.security.user.iservice.IAddressService;
import com.ajith.security.user.model.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/address")
@RequiredArgsConstructor
public class AddressController {

    private final IAddressService iAddressService;
    @PostMapping ("/add")
    public ResponseEntity < BasicResponse > addAddress(
            @RequestBody AddressRequest request,
            @RequestHeader("Authorization") String authHeader){
        ResponseEntity<BasicResponse> response = iAddressService.addAddress(request,authHeader);
        return response;
    }

    @GetMapping("/get")
    public ResponseEntity< Address > getAddress (
            @RequestHeader("Authorization") String authHeader){
        ResponseEntity< Address > response = iAddressService.getAddress(authHeader);
        return response;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BasicResponse> deleteAddress(
            @RequestHeader("Authorization") String authHeader) {
        ResponseEntity < BasicResponse > response = iAddressService.deleteAddress ( authHeader );
        return response;
    }

}
