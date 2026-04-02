package com.foodservice.frontend.util;

import com.foodservice.frontend.entity.dto.ApiResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@AllArgsConstructor
public class ApiGetRequest<T> {
    private final WebClient webClient;

    public T get(String url, Map<String, String> params, String token, ParameterizedTypeReference<ApiResponseDTO<T>> typeReference) {
        ApiResponseDTO<T> apiResponseDTO = webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path(url);
                    params.forEach(uriBuilder::queryParam);
                    return uriBuilder.build();
                })
                .cookie("token", token)
                .retrieve()
                .bodyToMono(typeReference)
                .block();

        if (apiResponseDTO != null && apiResponseDTO.getData() != null) {
            return apiResponseDTO.getData();
        }
        return null;
    }
}
