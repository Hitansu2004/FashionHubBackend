package com.nisum.inventoryService.exception;

public class SkuNotFoundException extends RuntimeException {
    public SkuNotFoundException(String message) {
        super(message);
    }
}
