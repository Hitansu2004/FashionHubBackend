package com.nisum.cartAndCheckout.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.cartAndCheckout.dto.request.CartItemRequestDTO;
import com.nisum.cartAndCheckout.dto.response.CartItemDto;
import com.nisum.cartAndCheckout.dto.response.CartItemResponseDTO;
import com.nisum.cartAndCheckout.dto.response.UpdateCartItemDto;
import com.nisum.cartAndCheckout.dto.response.UpdateCartItemSizeDto;
import com.nisum.cartAndCheckout.security.JwtUtil;
import com.nisum.cartAndCheckout.service.implementation.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
@AutoConfigureMockMvc(addFilters = false)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartServiceImpl cartServiceImpl;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    private final String token = "Bearer test.jwt.token";

    @BeforeEach
    void setup() {
        Mockito.when(jwtUtil.validateJwtToken(anyString())).thenReturn(true);
        Mockito.when(jwtUtil.getUserIdFromToken(anyString())).thenReturn(1);
    }

    // ✅ Add to Cart - Success
    @Test
    void testAddToCart_Success() throws Exception {
        CartItemRequestDTO request = CartItemRequestDTO.builder()
                .productId(1)
                .sku("sku1")
                .size("M")
                .finalPrice(BigDecimal.valueOf(99.99))
                .build();

        CartItemResponseDTO response = new CartItemResponseDTO("Item added successfully");

        Mockito.when(cartServiceImpl.addToCart(any(CartItemRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/cart/add")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item added successfully"));
    }

    // ✅ Add to Cart - Item already exists
    @Test
    void testAddToCart_ItemAlreadyExists() throws Exception {
        CartItemRequestDTO request = CartItemRequestDTO.builder()
                .productId(2)
                .sku("sku2")
                .size("L")
                .finalPrice(BigDecimal.valueOf(49.50))
                .build();

        CartItemResponseDTO response = new CartItemResponseDTO("Item already exists in cart");

        Mockito.when(cartServiceImpl.addToCart(any(CartItemRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/cart/add")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item already exists in cart"));
    }

    // ❌ Add to Cart - Unauthorized
    @Test
    void testAddToCart_Unauthorized() throws Exception {
        CartItemRequestDTO request = CartItemRequestDTO.builder()
                .productId(3)
                .sku("sku3")
                .size("S")
                .finalPrice(BigDecimal.valueOf(75.0))
                .build();

        Mockito.when(jwtUtil.validateJwtToken(anyString())).thenReturn(false);

        mockMvc.perform(post("/api/cart/add")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    // ✅ Get Cart Items
    @Test
    void testGetCartItems() throws Exception {
        CartItemDto item = new CartItemDto(1, "Product A", "M", List.of("S", "M", "L"), 2, 10, 100.0, 10.0, 180.0, "img.jpg");

        Mockito.when(cartServiceImpl.getCartItemsByUserId(1)).thenReturn(List.of(item));

        mockMvc.perform(get("/api/cart/items")
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productName").value("Product A"));
    }

    // ✅ Update Quantity - Success
    @Test
    void testUpdateQuantity() throws Exception {
        UpdateCartItemDto response = new UpdateCartItemDto("updated", 5);

        Mockito.when(cartServiceImpl.updateCartItemQuantity(1, 1, 3)).thenReturn(response);

        mockMvc.perform(put("/api/cart/updateQuantity")
                        .header("Authorization", token)
                        .param("cartItemId", "1")
                        .param("newQuantity", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("updated"))
                .andExpect(jsonPath("$.stockQuantity").value(5));
    }

    // ❌ Update Quantity - Unauthorized
    @Test
    void testUpdateQuantity_Unauthorized() throws Exception {
        Mockito.when(jwtUtil.validateJwtToken(anyString())).thenReturn(false);

        mockMvc.perform(put("/api/cart/updateQuantity")
                        .header("Authorization", token)
                        .param("cartItemId", "1")
                        .param("newQuantity", "3"))
                .andExpect(status().isUnauthorized());
    }

    // ✅ Update Size - Success
    @Test
    void testUpdateSize() throws Exception {
        UpdateCartItemSizeDto response = new UpdateCartItemSizeDto("Size Updated", 7, 100.0, 10.0, 180.0);

        Mockito.when(cartServiceImpl.updateCartItemSize(1, 1, "L")).thenReturn(response);

        mockMvc.perform(put("/api/cart/updateSize")
                        .header("Authorization", token)
                        .param("cartItemId", "1")
                        .param("size", "L"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Size Updated"));
    }

    // ❌ Update Size - Unauthorized
    @Test
    void testUpdateSize_Unauthorized() throws Exception {
        Mockito.when(jwtUtil.validateJwtToken(anyString())).thenReturn(false);

        mockMvc.perform(put("/api/cart/updateSize")
                        .header("Authorization", token)
                        .param("cartItemId", "1")
                        .param("size", "L"))
                .andExpect(status().isUnauthorized());
    }

    // ✅ Delete Cart Item - Success
    @Test
    void testDeleteCartItem() throws Exception {
        Mockito.when(cartServiceImpl.deleteCartItem(1, 1)).thenReturn(Map.of("message", "deleted"));

        mockMvc.perform(delete("/api/cart/deleteCartItem")
                        .header("Authorization", token)
                        .param("cartItemId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("deleted"));
    }

    // ❌ Delete Cart Item - Unauthorized
    @Test
    void testDeleteCartItem_Unauthorized() throws Exception {
        Mockito.when(jwtUtil.validateJwtToken(anyString())).thenReturn(false);

        mockMvc.perform(delete("/api/cart/deleteCartItem")
                        .header("Authorization", token)
                        .param("cartItemId", "1"))
                .andExpect(status().isUnauthorized());
    }
}