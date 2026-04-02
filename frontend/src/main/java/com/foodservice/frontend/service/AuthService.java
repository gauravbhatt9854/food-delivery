package com.foodservice.frontend.service;

import com.foodservice.frontend.entity.dto.UserDTO;

import java.util.List;

public interface AuthService {
    public List<String> userLogin(UserDTO userDTO);
}
