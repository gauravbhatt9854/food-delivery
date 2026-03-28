package com.foodservice.service.impl;

import com.foodservice.entity.Customer;
import com.foodservice.entity.DeliveryAddress;
import com.foodservice.entity.dto.CustomerAnalyticsDTO;
import com.foodservice.entity.dto.CustomerDTO;
import com.foodservice.entity.dto.OrderItemDetailDTO;
import com.foodservice.config.CustomMapper;
import com.foodservice.exception.ResourceNotFoundException;
import com.foodservice.repository.CustomerRepository;
import com.foodservice.repository.DeliveryAddressRepository;
import com.foodservice.repository.OrderRepository;
import com.foodservice.service.CustomerService;
import com.foodservice.constants.CustomerErrorConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final DeliveryAddressRepository addressRepository;
    private final OrderRepository orderRepository;

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
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();

        if (customers.isEmpty()) {
            throw new ResourceNotFoundException(
                    CustomerErrorConstant.NO_CUSTOMERS_FOUND
            );
        }

        return customers.stream()
                .map(CustomMapper::customerToCustomerDTO)
                .toList();
    }

    @Override
    public List<CustomerDTO> getCustomersByCity(String city) {
        List<CustomerDTO> customers = addressRepository.findByCityIgnoreCase(city)
                .stream()
                .map(DeliveryAddress::getCustomer)
                .distinct()
                .map(CustomMapper::customerToCustomerDTO)
                .toList();

        if (customers.isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format(CustomerErrorConstant.NO_CUSTOMERS_FOUND_IN_CITY, city)
            );
        }

        return customers;
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

        if (items == null || items.isEmpty()) {
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
}