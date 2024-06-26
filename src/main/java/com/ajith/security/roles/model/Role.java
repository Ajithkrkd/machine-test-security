package com.ajith.security.roles.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO )
    private int roleId;
    private String roleName;
    private boolean isDeleted =false;

}
