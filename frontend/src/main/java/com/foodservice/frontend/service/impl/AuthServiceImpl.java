package com.foodservice.frontend.service.impl;

import com.foodservice.frontend.entity.dto.ApiResponseDTO;
import com.foodservice.frontend.entity.dto.LoginResponseDTO;
import com.foodservice.frontend.entity.dto.UserDTO;
import com.foodservice.frontend.exception.InvalidCredentialsException;
import com.foodservice.frontend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final WebClient webClient;

    @Override
    public List<String> userLogin(UserDTO userDTO) {
        List<String> cookie = new ArrayList<>();
        Mono<LoginResponseDTO> response = webClient.post()
                .uri("/auth/login")
                .bodyValue(userDTO)
                .exchangeToMono(res -> {
                    if (res.statusCode().isError()) {
                        return res.bodyToMono(ApiResponseDTO.class)
                                .defaultIfEmpty(new ApiResponseDTO())
                                .flatMap(error -> Mono.error(
                                        new InvalidCredentialsException(error.getMessage())
                                ));
                    }
                    List<String> cookies = res.headers().header("Set-Cookie");
                    cookie.addAll(cookies);
                    return res.bodyToMono(LoginResponseDTO.class);
                });

        response.block();
        return cookie;
    }
}
