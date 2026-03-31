package com.foodservice.service.impl;

import com.foodservice.entity.dto.LoginRequestDTO;
import com.foodservice.entity.dto.LoginResponseDTO;
import com.foodservice.exception.ResourceNotFoundException;
import com.foodservice.security.JwtService;
import com.foodservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;

    @Value("${jwt.expiration}")
    private long expirationMs;

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        log.info("Login attempt for username: {}", request.getUsername());

        try {
            // 1. Verify username + password against the DB
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            log.warn("Failed login attempt for username: {}", request.getUsername());
            throw new ResourceNotFoundException("Invalid username or password");
        }

        // 2. Load the user and generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);

        log.info("Login successful for username: {}", request.getUsername());

        return new LoginResponseDTO(token, request.getUsername(), expirationMs);
    }
}