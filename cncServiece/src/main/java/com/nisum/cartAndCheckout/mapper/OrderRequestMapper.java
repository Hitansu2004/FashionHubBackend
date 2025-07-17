package com.nisum.cartAndCheckout.mapper;

import com.nisum.cartAndCheckout.dto.request.OrderItemRequestDTO;
import com.nisum.cartAndCheckout.dto.request.OrderRequestDTO;
import com.nisum.cartAndCheckout.entity.CartItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderRequestMapper {
    public static OrderRequestDTO toOrderRequestDTO(Integer userId, List<CartItem> cartItems, String promoValue, String paymentMethod, Integer addressId) {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart items are null or empty");
        }
        System.out.println(addressId);
        List<OrderItemRequestDTO> orderItemRequestDTOS = cartItems.stream()
                .peek(item -> {
                    if (item == null) {
                        throw new NullPointerException("CartItem is null");
                    }
                    if (item.getSku() == null) {
                        throw new NullPointerException("CartItem SKU is null");
                    }
                })
                .map(item -> OrderItemRequestDTO.builder()
                        .productId(item.getProductId())
                        .sku(item.getSku())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .discount(item.getDiscount())
                        .finalPrice(item.getFinalPrice())
                        .size(item.getSize())
                        .status("Ordered")
                        .sellerId(item.getSellerId())
                        .build())
                .collect(Collectors.toList());

        System.out.println("OrderItemRequestDTOS: " + orderItemRequestDTOS);
        BigDecimal totalOrderValue = cartItems.stream()
                .map(CartItem::getFinalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Convert string promoValue to BigDecimal
        BigDecimal promoDiscount = BigDecimal.ZERO;
        try {
            if (promoValue != null && !promoValue.isBlank()) {
                promoDiscount = new BigDecimal(promoValue);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid promo value: " + promoValue);
        }

        BigDecimal finalOrderTotal = totalOrderValue.subtract(promoDiscount);

        return OrderRequestDTO.builder()
                .userId(userId)
                .orderDate(LocalDateTime.now())
                .orderStatus("Pending")
                .promoDiscount(promoDiscount)
                .orderTotal(finalOrderTotal)
                .paymentMode(paymentMethod)
                .orderItemRequestDTOS(orderItemRequestDTOS)
                .addressId(addressId)
                .build();
    }
}
