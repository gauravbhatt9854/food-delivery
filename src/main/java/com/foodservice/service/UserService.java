package com.foodservice.service;

import com.foodservice.entity.dto.RegisterRequestDTO;
import com.foodservice.entity.dto.RegisterResponseDTO;

public interface UserService {
    RegisterResponseDTO registerUser(RegisterRequestDTO request);
}
