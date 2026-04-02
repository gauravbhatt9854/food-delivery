package com.foodservice.controller;

import com.foodservice.entity.dto.MenuItemResponseDTO;
import com.foodservice.exception.MenuItemNotFoundException;
import com.foodservice.exception.RestaurantNotFoundException;
import com.foodservice.security.JwtAuthFilter;
import com.foodservice.service.MenuItemService;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MenuItemController.class, excludeAutoConfiguration = {
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
@DisplayName("Menu Item Controller Tests")
class MenuItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuItemService menuItemService;

    @MockBean
    private com.foodservice.security.JwtService jwtService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @MockBean
    private com.foodservice.service.impl.UserDetailsServiceImpl userDetailsService;

    private MenuItemResponseDTO mockMenuItem;

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

        mockMenuItem = new MenuItemResponseDTO();
        mockMenuItem.setItemId(1);
        mockMenuItem.setItemName("Burger");
        mockMenuItem.setItemDescription("Delicious beef burger with cheese");
        mockMenuItem.setItemPrice(new BigDecimal("12.99"));
        mockMenuItem.setRestaurantId(1);
    }

    // ── GET /api/v1/menu-item/fetchByRestaurant/{restaurantId} ─────────────────────────────────────────

    @Test
    @DisplayName("PASS - GET /fetchByRestaurant/{restaurantId} returns 200 with menu items")
    void fetchMenuByRestaurantId_Pass() throws Exception {
        var page = new PageImpl<>(List.of(mockMenuItem));
        when(menuItemService.getMenuByRestaurantId(eq(1), anyInt(), anyInt())).thenReturn(page);

        mockMvc.perform(get("/api/v1/menu-item/fetchByRestaurant/{restaurantId}", 1)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Menu items fetched successfully for the given restaurant"))
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content[0].itemId").value(1))
                .andExpect(jsonPath("$.data.content[0].itemName").value("Burger"))
                .andExpect(jsonPath("$.data.content[0].itemDescription").value("Delicious beef burger with cheese"))
                .andExpect(jsonPath("$.data.content[0].itemPrice").value(12.99))
                .andExpect(jsonPath("$.data.content[0].restaurantId").value(1));
    }

    @Test
    @DisplayName("FAIL - GET /fetchByRestaurant/{restaurantId} throws RestaurantNotFoundException → handled by GlobalExceptionHandler → 404")
    void fetchMenuByRestaurantId_Fail_RestaurantNotFound() throws Exception {
        when(menuItemService.getMenuByRestaurantId(eq(999), anyInt(), anyInt()))
                .thenThrow(new RestaurantNotFoundException("Restaurant not found with ID: 999"));

        mockMvc.perform(get("/api/v1/menu-item/fetchByRestaurant/{restaurantId}", 999)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Restaurant not found with ID: 999"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}
