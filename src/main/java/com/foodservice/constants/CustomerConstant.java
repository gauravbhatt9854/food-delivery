package com.foodservice.constants;

public class CustomerConstant {

    private CustomerConstant() {
        // prevent instantiation
    }

    // Status Codes
    public static final int STATUS_200 = 200;

    // Messages
    public static final String MESSAGE_CUSTOMER_FETCHED = "Customer details retrieved successfully for the given customer ID";
    public static final String MESSAGE_CUSTOMERS_FETCHED = "All customer records retrieved successfully";
    public static final String MESSAGE_CUSTOMERS_FETCHED_BY_CITY = "Customer records retrieved successfully for the specified city";
    public static final String MESSAGE_ADDRESS_COUNT_FETCHED = "Total number of delivery addresses retrieved successfully for the customer";
    public static final String MESSAGE_CUSTOMER_ANALYTICS_FETCHED = "Customer analytics data retrieved successfully";
    public static final String MESSAGE_ADDRESSES_FETCHED = "All order records retrieved successfully";
    public static final String MESSAGE_CUSTOMER_ORDERS_FETCHED = "Customer orders retrieved successfully";
}