package com.foodservice.frontend.controller;
import com.foodservice.frontend.service.DeliveryAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/delivery-address")
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;

    @GetMapping("/{addressId}")
    public String getAddressById(@PathVariable Integer addressId, Model model
    ,  @CookieValue(name = "token", required = false) String token) {

        model.addAttribute("address", deliveryAddressService.getAddressById(addressId , token));
        return "pages/delivery-address/delivery-address-details";
    }

    @GetMapping()
    public String getByCity(@RequestParam String city, Model model ,
                            @CookieValue(name = "token", required = false) String token) {

        model.addAttribute("addresses", deliveryAddressService.getAddressesByCity(city , token));
        model.addAttribute("city", city);
        return "pages/delivery-address/delivery-address-list";
    }
}
