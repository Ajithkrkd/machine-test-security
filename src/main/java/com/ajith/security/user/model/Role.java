package com.ajith.security.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO )
    private int roleId;
    private String roleName;
    private boolean isDeleted =false;

}
