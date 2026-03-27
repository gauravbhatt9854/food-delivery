package com.foodservice.config;

import com.foodservice.entity.Customer;
import com.foodservice.entity.dto.CustomerDTO;

public class CustomMapper {

        public static CustomerDTO customerToCustomerDTO(Customer customer) {
            CustomerDTO dto = new CustomerDTO();
            dto.setId(customer.getId());
            dto.setName(customer.getName());
            dto.setEmail(customer.getEmail());
            dto.setPhone(customer.getPhone());
            return dto;
        }

        public static Customer customerDTOToCustomer(CustomerDTO dto) {
            Customer customer = new Customer();
            customer.setId(dto.getId());
            customer.setName(dto.getName());
            customer.setEmail(dto.getEmail());
            customer.setPhone(dto.getPhone());
            return customer;
        }
}
