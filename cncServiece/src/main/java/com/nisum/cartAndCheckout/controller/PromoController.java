package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.dto.PromoDTO;
import com.nisum.cartAndCheckout.dto.response.PromoResponseDto;
import com.nisum.cartAndCheckout.exception.PromoServiceUnavailableException;
import com.nisum.cartAndCheckout.security.JwtUtil;
import com.nisum.cartAndCheckout.service.interfaces.PromoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/checkout-promo")
public class PromoController {

    private final PromoService promoService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public PromoController(PromoService promoService) {
        this.promoService = promoService;
    }

    // Helper method to get userId from JWT token
    private Integer getUserIdFromToken(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        if (token != null && jwtUtil.validateJwtToken(token)) {
            return jwtUtil.getUserIdFromToken(token);
        }
        throw new RuntimeException("Invalid or missing authentication token");
    }

    // Helper method to extract token from request
    private String getTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    @PostMapping("/promos")
    public ResponseEntity<List<PromoDTO>> getPromosForProducts(@RequestBody List<Integer> productIds) {
        try {
            List<PromoDTO> result = promoService.getPromosForProducts(productIds);
            return ResponseEntity.ok(result);
        } catch (PromoServiceUnavailableException e) {
            return ResponseEntity.status(500).body(null);  // Change from 503 to 500
        }
    }

    @PostMapping("/validate-promo")
    public ResponseEntity<PromoResponseDto> validatePromo(@RequestParam String promoCode,HttpServletRequest request) {

        Integer userId = jwtUtil.getUserIdFromToken(request.getHeader("Authorization").substring(7));
        PromoResponseDto response = promoService.validatePromoCode(promoCode, userId);

        return ResponseEntity.ok(response);


    }


}