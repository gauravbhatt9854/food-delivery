package com.foodservice.controller;

import com.foodservice.constants.AuthConstant;
import com.foodservice.entity.dto.ApiResponseDTO;
import com.foodservice.entity.dto.LoginRequestDTO;
import com.foodservice.entity.dto.LoginResponseDTO;
import com.foodservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        ResponseCookie responseCookie = ResponseCookie.from("token", response.getToken())
                .path("/api/v1")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(new ApiResponseDTO(
                        AuthConstant.STATUS_200,
                        AuthConstant.MESSAGE_LOGIN_SUCCESS,
                        response
                ));
    }

    @GetMapping ("/logout")
    public ResponseEntity<ApiResponseDTO> logout() {
        ResponseCookie responseCookie = ResponseCookie.from("token", "")
                .path("/api/v1")
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(new ApiResponseDTO(
                        AuthConstant.STATUS_200,
                        AuthConstant.MESSAGE_LOGOUT_SUCCESS,
                        null
                ));
    }
}