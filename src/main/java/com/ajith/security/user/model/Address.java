package com.ajith.security.user.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private int addressId;
    private String houseName;
    private String district;
    private String city;
    private String country;
    private Long postalCode;

}
