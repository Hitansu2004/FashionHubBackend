package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.entity.CartItem;
import com.nisum.cartAndCheckout.entity.ShoppingCart;
import com.nisum.cartAndCheckout.repository.CartItemRepository;
import com.nisum.cartAndCheckout.repository.ShoppingCartRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BillingController.class)
class BillingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartRepository shoppingCartRepository;

    @MockBean
    private CartItemRepository cartItemRepository;

    @Test
    void testGenerateBill_Success() throws Exception {
        ShoppingCart cart = new ShoppingCart();
        cart.setCartId(1);
        Mockito.when(shoppingCartRepository.findByUserId(101)).thenReturn(Optional.of(cart));
        CartItem item = new CartItem();
        item.setId(1);
        Mockito.when(cartItemRepository.findByCart(cart)).thenReturn(List.of(item));
        Mockito.doNothing().when(cartItemRepository).deleteAll(any());
        mockMvc.perform(get("/api/bill/101"))
                .andExpect(status().isOk());
    }

    @Test
    void testGenerateBill_CartNotFound() throws Exception {
        Mockito.when(shoppingCartRepository.findByUserId(999)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/bill/999"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGenerateBill_EmptyCart() throws Exception {
        ShoppingCart cart = new ShoppingCart();
        cart.setCartId(2);
        Mockito.when(shoppingCartRepository.findByUserId(102)).thenReturn(Optional.of(cart));
        Mockito.when(cartItemRepository.findByCart(cart)).thenReturn(List.of());
        mockMvc.perform(get("/api/bill/102"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGenerateBill_Exception() throws Exception {
        Mockito.when(shoppingCartRepository.findByUserId(anyInt())).thenThrow(new RuntimeException("DB error"));
        mockMvc.perform(get("/api/bill/101"))
                .andExpect(status().isInternalServerError());
    }
}
