package com.foodservice.frontend.controller;

import com.foodservice.frontend.entity.dto.UserDTO;
import com.foodservice.frontend.service.AuthService;
import org.springframework.http.HttpHeaders;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String login(@CookieValue(name = "token", required = false) String token) {

        if (token != null) {
            return "redirect:/";
        }

        return "pages/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserDTO userDTO, HttpServletResponse response) {

        List<String> tokens = authService.userLogin(userDTO);

        for (String token : tokens) {
            response.addHeader(HttpHeaders.SET_COOKIE, token);
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        response.addHeader(HttpHeaders.SET_COOKIE, "token=; Path=/; HttpOnly; Secure; SameSite=None; Max-Age=0");
        return "redirect:/auth/login";
    }
}