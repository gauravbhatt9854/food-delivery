package com.foodservice.service;

import com.foodservice.entity.dto.LoginRequestDTO;
import com.foodservice.entity.dto.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO request);
}