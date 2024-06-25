package com.ajith.security.roles.repository;

import com.ajith.security.roles.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository< Role,Integer > {
}
