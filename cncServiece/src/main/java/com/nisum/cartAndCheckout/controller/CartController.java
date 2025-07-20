package com.nisum.cartAndCheckout.controller;
 
import com.nisum.cartAndCheckout.dto.request.CartItemRequestDTO;
import com.nisum.cartAndCheckout.dto.response.CartItemDto;
import com.nisum.cartAndCheckout.dto.response.CartItemResponseDTO;
import com.nisum.cartAndCheckout.dto.response.UpdateCartItemDto;
import com.nisum.cartAndCheckout.dto.response.UpdateCartItemSizeDto;
import com.nisum.cartAndCheckout.security.JwtUtil;
import com.nisum.cartAndCheckout.service.implementation.CartServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
import java.util.Map;
 
@RestController
@RequestMapping("/api/cart")
public class CartController {
 
    private final CartServiceImpl cartServiceImpl;
 
    @Autowired
    private JwtUtil jwtUtil;
 
    public CartController(CartServiceImpl cartServiceImpl) {
        this.cartServiceImpl = cartServiceImpl;
    }
 
    @PostMapping("/add")
    public ResponseEntity<CartItemResponseDTO> addToCart(@RequestBody CartItemRequestDTO dto, HttpServletRequest httpServletRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(httpServletRequest.getHeader("Authorization").substring(7));
        dto.setUserId(userId);
        CartItemResponseDTO response = cartServiceImpl.addToCart(dto);
        return ResponseEntity.ok(response);
    }
 
    @GetMapping("/items")
    public ResponseEntity<List<CartItemDto>> getCartItems(HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader("Authorization");
 
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(null); // Unauthorized
        }
 
        String token = authHeader.substring(7);
        Integer userId = jwtUtil.getUserIdFromToken(token);
 
        if (userId == null) {
            return ResponseEntity.status(401).body(null); // Unauthorized
        }
 
        System.out.println("The current user ID: " + userId);
        List<CartItemDto> items = cartServiceImpl.getCartItemsByUserId(userId);
        return ResponseEntity.ok(items);
    }
 
 
    @PutMapping("/updateQuantity")
    public ResponseEntity<UpdateCartItemDto> updateQuantity(
            @RequestParam int cartItemId,
            @RequestParam int newQuantity,
            HttpServletRequest httpServletRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(httpServletRequest.getHeader("Authorization").substring(7));
        UpdateCartItemDto response = cartServiceImpl.updateCartItemQuantity(userId, cartItemId, newQuantity);
        return ResponseEntity.ok(response);
    }
 
    @PutMapping("/updateSize")
    public ResponseEntity<UpdateCartItemSizeDto> updateSize(
            @RequestParam int cartItemId,
            @RequestParam String size,
            HttpServletRequest httpServletRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(httpServletRequest.getHeader("Authorization").substring(7));
        UpdateCartItemSizeDto response = cartServiceImpl.updateCartItemSize(userId, cartItemId, size);
        return ResponseEntity.ok(response);
    }
 
    @DeleteMapping("/deleteCartItem")
    public ResponseEntity<Map<String, Object>> deleteItem(@RequestParam int cartItemId,HttpServletRequest httpServletRequest) {
        Integer userId = jwtUtil.getUserIdFromToken(httpServletRequest.getHeader("Authorization").substring(7));
        Map<String, Object> response = cartServiceImpl.deleteCartItem(userId, cartItemId);
        return ResponseEntity.ok(response);
    }
}