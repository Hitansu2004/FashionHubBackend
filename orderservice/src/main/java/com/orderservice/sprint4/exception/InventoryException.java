package com.orderservice.sprint4.exception;

import org.springframework.web.client.RestClientException;

public class InventoryException extends RuntimeException {
    public InventoryException(String message, RestClientException ex) {
        super(message);
    }
}
