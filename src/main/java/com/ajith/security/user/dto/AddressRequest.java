package com.ajith.security.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressRequest {
    private String houseName;
    private String district;
    private String city;
    private String country;
    private Long postalCode;
}
