package com.foodservice.service.impl;
import com.foodservice.config.CustomMapper;
import com.foodservice.entity.Customer;
import com.foodservice.entity.dto.CustomerDTO;
import com.foodservice.respository.CustomerRepository;
import com.foodservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    // Get all customers
    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomMapper::customerToCustomerDTO)
                .toList();
    }

    // Get by ID
    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        return CustomMapper.customerToCustomerDTO(customer);
    }
}