package com.ajith.security.user.iservice;

import com.ajith.security.user.dto.AddressRequest;
import com.ajith.security.user.dto.BasicResponse;
import com.ajith.security.user.model.Address;
import org.springframework.http.ResponseEntity;

public interface IAddressService {
    ResponseEntity< BasicResponse> addAddress (AddressRequest request ,String authHeader);

    ResponseEntity< Address > getAddress (String authHeader);

    ResponseEntity< BasicResponse> deleteAddress (String authHeader);

    ResponseEntity< BasicResponse> updateAddress (AddressRequest request, String authHeader);
}
