package com.nisum.productmanagement.exception;

public class SellerNotFoundException extends RuntimeException {
    public SellerNotFoundException(String message) {
        super(message);
    }
}
