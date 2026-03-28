package com.foodservice.service.impl;

import com.foodservice.config.CustomMapper;
import com.foodservice.entity.DeliveryAddress;
import com.foodservice.entity.dto.DeliveryAddressDTO;
import com.foodservice.repository.DeliveryAddressRepository;
import com.foodservice.service.DeliveryAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    private final DeliveryAddressRepository repository;

    @Override
    public List<DeliveryAddressDTO> getAddressesByCustomerId(Integer customerId) {
        return repository.findByCustomerCustomerId(customerId)
                .stream()
                .map(CustomMapper::deliveryAddressToDTO)
                .toList();
    }

    @Override
    public DeliveryAddressDTO getAddressById(Integer addressId) {
        DeliveryAddress address = repository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        return CustomMapper.deliveryAddressToDTO(address);
    }

    @Override
    public Integer getAddressCount(Integer customerId) {
        return repository.countByCustomerCustomerId(customerId);
    }

    @Override
    public List<DeliveryAddressDTO> getAddressesByCity(String city) {
        return repository.findByCityIgnoreCase(city)
                .stream()
                .map(CustomMapper::deliveryAddressToDTO)
                .toList();
    }

    @Override
    public DeliveryAddressDTO getDefaultAddress(Integer customerId) {
        DeliveryAddress address = repository.findFirstByCustomerCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("No address found"));

        return CustomMapper.deliveryAddressToDTO(address);
    }
}