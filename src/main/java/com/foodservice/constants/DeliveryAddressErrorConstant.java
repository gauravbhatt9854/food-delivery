package com.foodservice.constants;

public class DeliveryAddressErrorConstant {

    private DeliveryAddressErrorConstant() {}

    public static final String ADDRESS_NOT_FOUND = "Delivery address not found with id: %s";
    public static final String NO_ADDRESSES_FOUND_FOR_CUSTOMER = "No delivery addresses found for customer id: %s";
    public static final String NO_ADDRESSES_FOUND_IN_CITY = "No delivery addresses found for city: %s";
    public static final String DEFAULT_ADDRESS_NOT_FOUND = "Default delivery address not found for customer id: %s";
}