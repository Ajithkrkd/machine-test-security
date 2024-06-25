package com.ajith.security.user.service;

import com.ajith.security.user.iservice.IUserService;
import com.ajith.security.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    @Override
    public boolean isEmailExist (String email) {
        return userRepository.existsByEmail (email);
    }
}
