package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.dto.AddressDto;
import com.nisum.cartAndCheckout.entity.UserAddress;
import com.nisum.cartAndCheckout.service.implementation.AddressService;
import com.nisum.cartAndCheckout.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkout/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private JwtUtil jwtUtil;

    // Helper method to get userId from JWT token
    private Integer getUserIdFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validateJwtToken(token)) {
                return jwtUtil.getUserIdFromToken(token);
            }
        }
        throw new RuntimeException("Invalid or missing authentication token");
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserAddress>> getAllAddresses(HttpServletRequest request) {
        try {
            Integer userId = getUserIdFromToken(request);
            List<UserAddress> addresses = addressService.getAllAddressesByUserId(userId);
            return ResponseEntity.ok(addresses);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<UserAddress> addAddress(@RequestBody AddressDto dto, HttpServletRequest request) {
        try {
            Integer userId = getUserIdFromToken(request);
            dto.setUserId(userId); // Set the user ID from token
            UserAddress address = addressService.addAddress(dto);
            return ResponseEntity.ok(address);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<UserAddress> updateAddress(@RequestBody AddressDto dto, 
                                                    @RequestParam Integer addressId,
                                                    HttpServletRequest request) {
        try {
            Integer userId = getUserIdFromToken(request);
            dto.setUserId(userId);
            UserAddress address = addressService.updateAddress(addressId, dto);
            return ResponseEntity.ok(address);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAddress(@RequestParam Integer addressId, HttpServletRequest request) {
        try {
            Integer userId = getUserIdFromToken(request);
            addressService.deleteAddressById(addressId, userId);
            return ResponseEntity.ok("Address deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/get")
    public ResponseEntity<UserAddress> getAddressById(@RequestParam Integer addressId, HttpServletRequest request) {
        try {
            Integer userId = getUserIdFromToken(request);
            UserAddress address = addressService.getAddressById(addressId, userId);
            return ResponseEntity.ok(address);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }
}
