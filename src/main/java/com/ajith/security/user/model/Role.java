package com.ajith.security.user.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO )
    private int roleId;
    private String roleName;
    private String roleDescription;
    private boolean isDeleted;

    @OneToOne (mappedBy = "role")
    private User user;

}
