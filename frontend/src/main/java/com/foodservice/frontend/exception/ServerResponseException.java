package com.foodservice.frontend.exception;

public class ServerResponseException extends RuntimeException{
    public ServerResponseException(String message) {
        super(message);
    }
}
