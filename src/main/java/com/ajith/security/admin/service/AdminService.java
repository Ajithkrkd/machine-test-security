package com.ajith.security.admin.service;

import com.ajith.security.user.dto.UserDetailsResponse;
import com.ajith.security.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService implements IAdminService{

    private final UserRepository userRepository;
    @Override
    public ResponseEntity < List < UserDetailsResponse > > getAllUsers ( ) {
        return
    }
}
