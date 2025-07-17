package com.nisum.cartAndCheckout.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlaceOrderRequestDTO {
    private String promoCode;
    private String paymentMethod;
    private Integer shippingAddressId;
}