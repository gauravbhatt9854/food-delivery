package com.foodservice.controller;

import com.foodservice.constants.AuthConstant;
import com.foodservice.entity.dto.ApiResponseDTO;
import com.foodservice.entity.dto.LoginRequestDTO;
import com.foodservice.entity.dto.LoginResponseDTO;
import com.foodservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {

        log.info("Received login request for username: {}", request.getUsername());

        LoginResponseDTO response = authService.login(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponseDTO(
                        AuthConstant.STATUS_200,
                        AuthConstant.MESSAGE_LOGIN_SUCCESS,
                        response
                ));
    }
}