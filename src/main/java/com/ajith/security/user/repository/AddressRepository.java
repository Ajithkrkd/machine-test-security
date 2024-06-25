package com.ajith.security.user.repository;

import com.ajith.security.user.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository< Address,Integer > {
}
