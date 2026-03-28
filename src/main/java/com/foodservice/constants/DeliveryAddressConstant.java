package com.foodservice.constants;

public class DeliveryAddressConstant {

    private DeliveryAddressConstant() {
        // prevent instantiation
    }

    // Status Codes
    public static final int STATUS_200 = 200;

    // Messages
    public static final String MESSAGE_ADDRESSES_FETCHED = "Delivery address list retrieved successfully for the customer";
    public static final String MESSAGE_ADDRESS_FETCHED = "Delivery address details retrieved successfully for the given address ID";
    public static final String MESSAGE_ADDRESS_COUNT_FETCHED = "Total number of delivery addresses retrieved successfully for the customer";
    public static final String MESSAGE_ADDRESSES_FETCHED_BY_CITY = "Delivery addresses retrieved successfully for the specified city";
    public static final String MESSAGE_DEFAULT_ADDRESS_FETCHED = "Default delivery address retrieved successfully for the customer";
}