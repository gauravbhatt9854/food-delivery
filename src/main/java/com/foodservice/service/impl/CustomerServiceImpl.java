package com.foodservice.service.impl;

import com.foodservice.constants.DeliveryAddressConstant;
import com.foodservice.constants.DeliveryAddressErrorConstant;
import com.foodservice.entity.Customer;
import com.foodservice.entity.DeliveryAddress;
import com.foodservice.entity.dto.*;
import com.foodservice.config.CustomMapper;
import com.foodservice.exception.ResourceNotFoundException;
import com.foodservice.repository.CustomerRepository;
import com.foodservice.repository.DeliveryAddressRepository;
import com.foodservice.repository.OrderRepository;
import com.foodservice.service.CustomerService;
import com.foodservice.constants.CustomerErrorConstant;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final DeliveryAddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;

    @Override
    public CustomerDTO getCustomerById(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                String.format(CustomerErrorConstant.CUSTOMER_NOT_FOUND, customerId)
                        )
                );

        return CustomMapper.customerToCustomerDTO(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customers = customerRepository.findAll(pageable);

        return customers
                .map(CustomMapper::customerToCustomerDTO)
                .getContent();   //  convert Page → List
    }


    @Override
    public List<CustomerDTO> getCustomersByCity(String city, @Min(0) Integer page, @Min(1) Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Customer> customers = customerRepository.findCustomersByCity(city , pageable);

        return customers
                .map(CustomMapper::customerToCustomerDTO)
                .getContent();   //  convert Page → List
    }

    @Override
    public Integer getAddressCount(Integer customerId) {

        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException(
                    String.format(CustomerErrorConstant.CUSTOMER_NOT_FOUND, customerId)
            );
        }

        return addressRepository.countByCustomerCustomerId(customerId);
    }

    @Override
    public CustomerAnalyticsDTO getCustomerAnalytics(Integer customerId) {

        List<OrderItemDetailDTO> items =
                orderRepository.getOrderDetailsByCustomerId(customerId);

        if (items == null) {
            throw new ResourceNotFoundException(
                    String.format(CustomerErrorConstant.NO_ORDER_DATA_FOUND, customerId)
            );
        }

        Integer totalOrders = (int) items.stream()
                .map(OrderItemDetailDTO::getOrderDate)
                .distinct()
                .count();

        Double totalSpend = items.stream()
                .mapToDouble(i -> i.getQuantity() * i.getItemPrice().doubleValue())
                .sum();

        Double avgOrderValue = totalOrders == 0
                ? 0.0
                : totalSpend / totalOrders;

        return new CustomerAnalyticsDTO(
                totalOrders,
                totalSpend,
                avgOrderValue
        );
    }

    @Override
    public List<OrderItemDetailDTO> getOrdersByCustomerId(Integer customerId) {
        List<OrderItemDetailDTO> orders = orderRepository.getOrderDetailsByCustomerId(customerId);
        return orders;
    }

    @Override
    public List<DeliveryAddressDTO> getAddressesByCustomerId(Integer customerId) {
        List<DeliveryAddressDTO> deliveryAddressDTOS =
                deliveryAddressRepository.findByCustomerCustomerId(customerId)
                        .stream().map(CustomMapper::deliveryAddressToDTO)
                        .toList();

        return deliveryAddressDTOS;
    }
}