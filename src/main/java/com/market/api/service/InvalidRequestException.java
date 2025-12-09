package com.market.api.service;

/**
 * Exception thrown when request validation fails
 */
public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }
}
