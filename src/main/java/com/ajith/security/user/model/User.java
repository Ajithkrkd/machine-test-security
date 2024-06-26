package com.ajith.security.user.model;


import com.ajith.security.roles.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "userEntity")
public class User implements UserDetails {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO )
    private int userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private boolean isBlocked =false;
    private boolean isActive = false;

    @JsonIgnore
    @OneToOne
    @JoinColumn (name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public Collection < ? extends GrantedAuthority > getAuthorities ( ) {
        return List.of ( new SimpleGrantedAuthority (   role.getRoleName () ));
    }

    @Override
    public String getPassword ( ) {
        return password;
    }

    @Override
    public String getUsername ( ) {
        return email;
    }

    @Override
    public boolean isAccountNonExpired ( ) {
        return true;
    }

    @Override
    public boolean isAccountNonLocked ( ) {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired ( ) {
        return true;
    }

    @Override
    public boolean isEnabled ( ) {
        return true;
    }
}
