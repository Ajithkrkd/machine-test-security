package com.ajith.security.user.repository;

import com.ajith.security.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository< User ,Integer > {
    Optional< User> findByEmail (String username);

    boolean existsByEmail (String email);

    List< User> findAllByIsBlockedFalse ( );
}
