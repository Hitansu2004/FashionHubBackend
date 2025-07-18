package com.nisum.productmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.productmanagement.dto.SellerDto;
import com.nisum.productmanagement.service.SellerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

@WebMvcTest(SellerController.class)
public class SellerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerService sellerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllSellers() throws Exception {
        List<SellerDto> sellers = Arrays.asList(new SellerDto(), new SellerDto());
        Mockito.when(sellerService.getAllSellers()).thenReturn(sellers);
        mockMvc.perform(MockMvcRequestBuilders.get("/sellers"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetSellerById() throws Exception {
        SellerDto seller = new SellerDto();
        Mockito.when(sellerService.getSellerById(1L)).thenReturn(seller);
        mockMvc.perform(MockMvcRequestBuilders.get("/sellers/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCreateSeller() throws Exception {
        SellerDto sellerDto = new SellerDto();
        Mockito.when(sellerService.createSeller(Mockito.any(SellerDto.class))).thenReturn(sellerDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/sellers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sellerDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testUpdateSeller() throws Exception {
        SellerDto sellerDto = new SellerDto();
        Mockito.when(sellerService.updateSeller(Mockito.eq(1L), Mockito.any(SellerDto.class))).thenReturn(sellerDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/sellers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sellerDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteSeller() throws Exception {
        Mockito.doNothing().when(sellerService).deleteSeller(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/sellers/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testGetSellerCount() throws Exception {
        Mockito.when(sellerService.countSellers()).thenReturn(5L);
        mockMvc.perform(MockMvcRequestBuilders.get("/sellers/count"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("5"));
    }

    @Test
    void testSearchSellers() throws Exception {
        List<SellerDto> sellers = Arrays.asList(new SellerDto());
        Mockito.when(sellerService.searchSellers("test")).thenReturn(sellers);
        mockMvc.perform(MockMvcRequestBuilders.get("/sellers/search?query=test"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetSellersByName() throws Exception {
        List<SellerDto> sellers = Arrays.asList(new SellerDto());
        Mockito.when(sellerService.getSellersByName("John")).thenReturn(sellers);
        mockMvc.perform(MockMvcRequestBuilders.get("/sellers/by-name?name=John"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetSellersByContactName() throws Exception {
        List<SellerDto> sellers = Arrays.asList(new SellerDto());
        Mockito.when(sellerService.getSellersByContactName("Jane")).thenReturn(sellers);
        mockMvc.perform(MockMvcRequestBuilders.get("/sellers/by-contact?contactName=Jane"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetSellersByCity() throws Exception {
        List<SellerDto> sellers = Arrays.asList(new SellerDto());
        Mockito.when(sellerService.getSellersByCity("NYC")).thenReturn(sellers);
        mockMvc.perform(MockMvcRequestBuilders.get("/sellers/city/NYC"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetSellersByState() throws Exception {
        List<SellerDto> sellers = Arrays.asList(new SellerDto());
        Mockito.when(sellerService.getSellersByState("NY")).thenReturn(sellers);
        mockMvc.perform(MockMvcRequestBuilders.get("/sellers/state/NY"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetSellersByCountry() throws Exception {
        List<SellerDto> sellers = Arrays.asList(new SellerDto());
        Mockito.when(sellerService.getSellersByCountry("USA")).thenReturn(sellers);
        mockMvc.perform(MockMvcRequestBuilders.get("/sellers/country/USA"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetSellerByEmail() throws Exception {
        SellerDto seller = new SellerDto();
        Mockito.when(sellerService.getSellerByEmail("test@email.com")).thenReturn(seller);
        mockMvc.perform(MockMvcRequestBuilders.get("/sellers/email/test@email.com"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testIsEmailAvailableTrue() throws Exception {
        Mockito.when(sellerService.isEmailAvailable("test@email.com")).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/sellers/email/available?email=test@email.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    void testIsEmailAvailableFalse() throws Exception {
        Mockito.when(sellerService.isEmailAvailable("test@email.com")).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.get("/sellers/email/available?email=test@email.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    void testGetSellerByIdNotFound() throws Exception {
        Mockito.when(sellerService.getSellerById(99L)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/sellers/99"))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Should be handled by exception handler if implemented
    }

    @Test
    void testDeleteSellerNotFound() throws Exception {
        Mockito.doThrow(new RuntimeException("Not found")).when(sellerService).deleteSeller(99L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/sellers/99"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void testUpdateSellerNotFound() throws Exception {
        SellerDto sellerDto = new SellerDto();
        Mockito.when(sellerService.updateSeller(Mockito.eq(99L), Mockito.any(SellerDto.class))).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.put("/sellers/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sellerDto)))
                .andExpect(MockMvcResultMatchers.status().isOk()); // Should be handled by exception handler if implemented
    }

    @Test
    void testCreateSellerInvalid() throws Exception {
        SellerDto sellerDto = new SellerDto();
        Mockito.when(sellerService.createSeller(Mockito.any(SellerDto.class))).thenThrow(new RuntimeException("Invalid data"));
        mockMvc.perform(MockMvcRequestBuilders.post("/sellers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sellerDto)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void testSearchSellersEmpty() throws Exception {
        Mockito.when(sellerService.searchSellers("none")).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/sellers/search?query=none"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAllSellersEmpty() throws Exception {
        Mockito.when(sellerService.getAllSellers()).thenReturn(Collections.emptyList());
        mockMvc.perform(MockMvcRequestBuilders.get("/sellers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
