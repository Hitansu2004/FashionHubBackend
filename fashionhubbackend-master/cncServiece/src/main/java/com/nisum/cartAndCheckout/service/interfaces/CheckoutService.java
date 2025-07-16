package com.nisum.cartAndCheckout.service.interfaces;

public interface CheckoutService {
    String placeOrder(Integer userId, String promoCode, String paymentMethod);
}
