package com.nisum.inventoryService.service.impl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.nisum.inventoryService.dto.ProductAttributeDto;
import com.nisum.inventoryService.dto.ProductDto;
import com.nisum.inventoryService.dto.ProductFlatResponseDTO;
import com.nisum.inventoryService.dto.ProductResponseDto;

class ProductServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProductServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProductsHandlesException() {
        // Simulate RestTemplate throwing exception
        when(restTemplate.getForObject(anyString(), eq(ProductResponseDto.class)))
                .thenThrow(new RuntimeException("Connection failed"));

        ProductResponseDto response = service.getAllProducts();

        assertNotNull(response);
        assertNull(response.getProducts());
    }

    @Test
    void testGetFlatProductListReturnsEmptyIfNoProducts() {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setProducts(null);

        when(restTemplate.getForObject(anyString(), eq(ProductResponseDto.class))).thenReturn(dto);

        ProductFlatResponseDTO result = service.getFlatProductList(0, 10);

        assertNotNull(result);
        assertTrue(result.getProducts() == null || result.getProducts().isEmpty());
    }

    @Test
    void testGetFlatProductListWithValidProducts() {
        // Create product attributes (SKUs)
        ProductAttributeDto attr1 = new ProductAttributeDto();
        attr1.setSku("SKU1");

        ProductAttributeDto attr2 = new ProductAttributeDto();
        attr2.setSku("SKU2");

        // Create product with attributes
        ProductDto product1 = new ProductDto();
        product1.setStatus("ACTIVE");
        product1.setCategoryId(1);
        product1.setAttributes(List.of(attr1, attr2));

        ProductResponseDto dto = new ProductResponseDto();
        dto.setProducts(List.of(product1));
        dto.setCurrentPage(0);
        dto.setPageSize(10);
        dto.setTotalPages(1);
        dto.setTotalResults(2);

        when(restTemplate.getForObject(anyString(), eq(ProductResponseDto.class))).thenReturn(dto);

        ProductFlatResponseDTO result = service.getFlatProductList(0, 10);

        assertNotNull(result);
        assertEquals(2, result.getProducts().size());

        ProductFlatResponseDTO.ProductFlatItemDTO flat1 = result.getProducts().get(0);
        assertEquals("SKU1", flat1.getSku());
        assertEquals(1, flat1.getCategoryId());
        assertEquals("ACTIVE", flat1.getStatus());

        ProductFlatResponseDTO.ProductFlatItemDTO flat2 = result.getProducts().get(1);
        assertEquals("SKU2", flat2.getSku());
    }
}
