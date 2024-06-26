package com.ajith.security.roles.repository;

import com.ajith.security.roles.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository< Role,Integer > {
    Optional< Role> findByRoleName (String roleUser);
}
