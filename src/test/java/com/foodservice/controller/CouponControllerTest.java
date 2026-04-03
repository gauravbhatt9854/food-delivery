package com.foodservice.controller;

import com.foodservice.entity.dto.OrderCouponDTO;
import com.foodservice.security.JwtAuthFilter;
import com.foodservice.service.CouponService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = CouponController.class,
        excludeAutoConfiguration = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientWebSecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration.class,
                org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyAutoConfiguration.class
        }
)
@DisplayName("Coupon Controller Tests")
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponService couponService;

    @MockBean
    private com.foodservice.security.JwtService jwtService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @MockBean
    private com.foodservice.service.impl.UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() throws Exception {
        // bypass JWT filter
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
    }

    //SUCCESS CASE
    @Test
    @DisplayName("PASS - GET /coupons/order/{id} returns 200 with coupons")
    void getCouponsByOrder_Pass() throws Exception {

        OrderCouponDTO dto = new OrderCouponDTO("SAVE10", BigDecimal.valueOf(100));

        when(couponService.getCouponsByOrder(1))
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/coupons/order/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Coupons fetched successfully"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].couponCode").value("SAVE10"))
                .andExpect(jsonPath("$.data[0].discount").value(100));
    }

    // FAILURE CASE
    @Test
    @DisplayName("FAIL - GET /coupons/order/{id} throws ResourceNotFoundException → 404")
    void getCouponsByOrder_Fail_NoCoupons() throws Exception {

        when(couponService.getCouponsByOrder(999))
                .thenThrow(new com.foodservice.exception.ResourceNotFoundException("No coupons found"));

        mockMvc.perform(get("/api/v1/coupons/order/{id}", 999))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value(org.hamcrest.Matchers.containsString("No coupons found")));
    }
}