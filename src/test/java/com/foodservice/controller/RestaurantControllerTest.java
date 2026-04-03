package com.foodservice.controller;

import com.foodservice.entity.dto.RatingResponseDTO;
import com.foodservice.entity.dto.RestaurantResponseDTO;
import com.foodservice.entity.dto.TopRatedRestaurantDTO;
import com.foodservice.exception.RestaurantNotFoundException;
import com.foodservice.security.JwtAuthFilter;
import com.foodservice.service.RatingService;
import com.foodservice.service.RestaurantService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RestaurantController.class, excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientWebSecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration.class
})
@TestPropertySource(properties = {
        "jwt.secret=testSecretKey12345678901234567890123456789012345678901234567890",
        "jwt.expiration=86400000"
})
@DisplayName("Restaurant Controller Tests")
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private RatingService ratingService;

    @MockBean
    private com.foodservice.security.JwtService jwtService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @MockBean
    private com.foodservice.service.impl.UserDetailsServiceImpl userDetailsService;

    private RestaurantResponseDTO mockRestaurant;
    private RatingResponseDTO mockRating;

    @BeforeEach
    void setUp() throws Exception {
        // make the mocked filter pass requests through to the controller
        Mockito.doAnswer(invocation -> {
            FilterChain chain = invocation.getArgument(2);
            chain.doFilter(
                    invocation.getArgument(0, HttpServletRequest.class),
                    invocation.getArgument(1, HttpServletResponse.class)
            );
            return null;
        }).when(jwtAuthFilter).doFilter(
                Mockito.any(HttpServletRequest.class),
                Mockito.any(HttpServletResponse.class),
                Mockito.any(FilterChain.class)
        );

        mockRestaurant = new RestaurantResponseDTO();
        mockRestaurant.setRestaurantName("Tasty Bites");
        mockRestaurant.setRestaurantAddress("123 Main St");
        mockRestaurant.setRestaurantPhone("+1234567890");

        mockRating = new RatingResponseDTO();
        mockRating.setRating(5);
        mockRating.setReview("Amazing food!");
        mockRating.setCustomerName("John Doe");
        mockRating.setCustomerPhone("+1234567890");
        mockRating.setOrderDate(LocalDateTime.of(2024, 1, 1, 12, 0));
    }

    // ── GET /api/v1/restaurants/fetch ─────────────────────────────────────────

    @Test
    @DisplayName("PASS - GET /fetch returns 200 with restaurant list")
    void fetchRestaurants_Pass() throws Exception {
        var page = new PageImpl<>(List.of(mockRestaurant));
        when(restaurantService.getAllRestaurants(anyInt(), anyInt())).thenReturn(page);

        mockMvc.perform(get("/api/v1/restaurants/fetch")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Restaurants fetched successfully"))
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content[0].restaurantName").value("Tasty Bites"))
                .andExpect(jsonPath("$.data.content[0].restaurantAddress").value("123 Main St"))
                .andExpect(jsonPath("$.data.content[0].restaurantPhone").value("+1234567890"));
    }

    @Test
    @DisplayName("FAIL - GET /fetch throws Exception → handled by GlobalExceptionHandler → 500")
    void fetchRestaurants_Fail_UnexpectedError() throws Exception {
        when(restaurantService.getAllRestaurants(anyInt(), anyInt()))
                .thenThrow(new RuntimeException("Unexpected database error"));

        mockMvc.perform(get("/api/v1/restaurants/fetch")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred. Please try again later."))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    // ── GET /api/v1/restaurants/{id}/ratings ──────────────────────────────────

    @Test
    @DisplayName("PASS - GET /{id}/ratings returns 200 with rating data")
    void fetchRestaurantRatings_Pass() throws Exception {
        var page = new PageImpl<>(List.of(mockRating));
        when(restaurantService.getRestaurantRatings(eq(1), anyInt(), anyInt())).thenReturn(page);

        mockMvc.perform(get("/api/v1/restaurants/{id}/ratings", 1)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Ratings fetched successfully"))
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content[0].rating").value(5))
                .andExpect(jsonPath("$.data.content[0].review").value("Amazing food!"))
                .andExpect(jsonPath("$.data.content[0].customerName").value("John Doe"))
                .andExpect(jsonPath("$.data.content[0].customerPhone").value("+1234567890"));
    }

    @Test
    @DisplayName("FAIL - GET /{id}/ratings throws RestaurantNotFoundException → handled by GlobalExceptionHandler → 404")
    void fetchRestaurantRatings_Fail_RestaurantNotFound() throws Exception {
        when(restaurantService.getRestaurantRatings(eq(999), anyInt(), anyInt()))
                .thenThrow(new RestaurantNotFoundException("Restaurant not found with ID: 999"));

        mockMvc.perform(get("/api/v1/restaurants/{id}/ratings", 999)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Restaurant not found with ID: 999"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }


    // TOP RATINGS TEST CASES FROM HERE---------------------------

    @Test
    @DisplayName("PASS - GET /top-rated returns 200 with top rated restaurants")
    void getTopRatedRestaurants_Pass() throws Exception {

        var dto = new com.foodservice.entity.dto.TopRatedRestaurantDTO(1, 4.5);
        var page = new PageImpl<>(List.of(dto));

        when(ratingService.getTopRatedRestaurants(anyInt(), anyInt()))
                .thenReturn(page);

        mockMvc.perform(get("/api/v1/restaurants/top-rated")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Top rated restaurants fetched successfully"))
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content[0].restaurantId").value(1))
                .andExpect(jsonPath("$.data.content[0].averageRating").value(4.5));
    }


    @Test
    @DisplayName("FAIL - GET /top-rated throws ResourceNotFoundException → 404")
    void getTopRatedRestaurants_Fail_NoData() throws Exception {

        when(ratingService.getTopRatedRestaurants(anyInt(), anyInt()))
                .thenThrow(new com.foodservice.exception.ResourceNotFoundException("No ratings available"));

        mockMvc.perform(get("/api/v1/restaurants/top-rated")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("404 NOT_FOUND, No ratings available"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }



    @Test
    @DisplayName("PASS - GET /top-rated uses default pagination")
    void getTopRatedRestaurants_DefaultParams() throws Exception {

        Page<TopRatedRestaurantDTO> page =
                new PageImpl<>(List.of());

        when(ratingService.getTopRatedRestaurants(0, 5)).thenReturn(page);

        mockMvc.perform(get("/api/v1/restaurants/top-rated"))
                .andExpect(status().isOk());

        verify(ratingService).getTopRatedRestaurants(0, 5);
    }
}