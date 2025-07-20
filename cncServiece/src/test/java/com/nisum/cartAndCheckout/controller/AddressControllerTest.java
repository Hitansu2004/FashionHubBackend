package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.entity.UserAddress;
import com.nisum.cartAndCheckout.dto.AddressDto;
import com.nisum.cartAndCheckout.service.implementation.AddressService;
import com.nisum.cartAndCheckout.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressController.class)
class AddressControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @MockBean
    private JwtUtil jwtUtil;

    private final String token = "Bearer test.jwt.token";

    @BeforeEach
    void setup() {
        Mockito.when(jwtUtil.validateJwtToken(anyString())).thenReturn(true);
        Mockito.when(jwtUtil.getUserIdFromToken(anyString())).thenReturn(101);
    }

    @Test
    void testGetAllAddresses_Success() throws Exception {
        UserAddress address = new UserAddress();
        address.setId(1);
        address.setUserId(101);
        Mockito.when(addressService.getAllAddressesByUserId(101)).thenReturn(List.of(address));
        mockMvc.perform(get("/api/checkout/address/all").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testGetAllAddresses_Unauthorized() throws Exception {
        Mockito.when(jwtUtil.validateJwtToken(anyString())).thenReturn(false);
        mockMvc.perform(get("/api/checkout/address/all").header("Authorization", token))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetAllAddresses_Empty() throws Exception {
        Mockito.when(addressService.getAllAddressesByUserId(101)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/checkout/address/all").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testAddAddress_Success() throws Exception {
        AddressDto dto = AddressDto.builder().userId(101).build();
        UserAddress address = new UserAddress();
        address.setId(1);
        address.setUserId(101);
        Mockito.when(addressService.addAddress(any(AddressDto.class), Mockito.eq(101))).thenReturn(address);
        mockMvc.perform(post("/api/checkout/address/add")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":101}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testAddAddress_Unauthorized() throws Exception {
        Mockito.when(jwtUtil.validateJwtToken(anyString())).thenReturn(false);
        mockMvc.perform(post("/api/checkout/address/add")
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":101}"))
                .andExpect(status().isUnauthorized());
    }
}
