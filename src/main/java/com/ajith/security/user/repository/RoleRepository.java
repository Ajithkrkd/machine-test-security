package com.ajith.security.user.repository;

import com.ajith.security.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository< Role,Integer > {
}
