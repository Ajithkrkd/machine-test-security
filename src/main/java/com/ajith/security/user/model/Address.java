package com.ajith.security.user.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private int addressId;
    private String houseName;
    private String district;
    private String city;
    private String country;
    private Long postalCode;
    private boolean isDeleted;
    @OneToOne (mappedBy = "address")
    private User user;
}
