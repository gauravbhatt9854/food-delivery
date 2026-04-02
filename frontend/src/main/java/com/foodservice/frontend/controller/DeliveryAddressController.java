package com.foodservice.frontend.controller;

import com.foodservice.frontend.service.DeliveryAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/delivery-address")
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;

    @GetMapping("/{addressId}")
    public String getAddressById(
            @PathVariable Integer addressId,
            @RequestParam Map<String, String> params,
            @CookieValue(name = "token", required = false) String token,
            Model model) {

        model.addAttribute("address",
                deliveryAddressService.getAddressById(addressId, params, token));

        return "pages/delivery-address/delivery-address-details";
    }

    @GetMapping()
    public String getByCity(
            @RequestParam Map<String, String> params,
            @CookieValue(name = "token", required = false) String token,
            Model model) {

        model.addAttribute("addresses",
                deliveryAddressService.getAddressesByCity(params, token));

        model.addAttribute("city", params.get("city"));

        return "pages/delivery-address/delivery-address-list";
    }
}