package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.dto.request.PlaceOrderRequestDTO;
import com.nisum.cartAndCheckout.exception.UserNotFoundException;
import com.nisum.cartAndCheckout.security.JwtUtil;
import com.nisum.cartAndCheckout.service.interfaces.CheckoutService;
import com.nisum.cartAndCheckout.validation.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final CheckoutService checkoutService;

    @Autowired
    public OrderController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderRequestDTO request, HttpServletRequest httpRequest) {

        Integer userId = jwtUtil.getUserIdFromToken(httpRequest.getHeader("Authorization").substring(7));
        String orderMessage = checkoutService.placeOrder(userId, request.getPromoCode(), request.getPaymentMethod(), request.getShippingAddressId(), httpRequest.getHeader("Authorization").substring(7));

        return ResponseEntity.ok().body("This Order Has: " + orderMessage);
    }

}
