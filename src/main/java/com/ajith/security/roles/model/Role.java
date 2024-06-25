package com.ajith.security.roles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
public class Role {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO )
    private int roleId;
    private String roleName;
    private boolean isDeleted =false;

}
