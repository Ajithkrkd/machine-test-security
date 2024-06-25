package com.ajith.security.user.repository;

import com.ajith.security.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository< User ,Integer > {
}
