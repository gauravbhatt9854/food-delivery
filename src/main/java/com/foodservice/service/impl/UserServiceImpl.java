package com.foodservice.service.impl;

import com.foodservice.entity.User;
import com.foodservice.entity.dto.RegisterRequestDTO;
import com.foodservice.entity.dto.RegisterResponseDTO;
import com.foodservice.repository.UserRepository;
import com.foodservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponseDTO registerUser(RegisterRequestDTO request) {
        
        log.info("Attempting to register user: {}", request.getUsername());

        // Check if user already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("Registration failed - username already exists: {}", request.getUsername());
            throw new RuntimeException("Username already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Save user
        User savedUser = userRepository.save(user);
        
        log.info("User registered successfully: {}", savedUser.getUsername());

        return new RegisterResponseDTO(
                savedUser.getUsername(),
                "User registered successfully"
        );
    }
}
