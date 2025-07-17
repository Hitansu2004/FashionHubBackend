package com.catalogservice.controller;

<<<<<<< HEAD
import com.catalogservice.dto.DiscountDto;
=======
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
import com.catalogservice.dto.ProductCategoryRequestDto;
import com.catalogservice.dto.ProductCategoryResponseDto;
import com.catalogservice.service.ProductCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

<<<<<<< HEAD
import java.math.BigInteger;
=======
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductCategoryControllerTest {
    @Mock
    private ProductCategoryService productCategoryService;

    @InjectMocks
    private ProductCategoryController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        ProductCategoryRequestDto request = new ProductCategoryRequestDto();
        ProductCategoryResponseDto response = new ProductCategoryResponseDto();
        when(productCategoryService.save(request)).thenReturn(response);
        ResponseEntity<ProductCategoryResponseDto> result = controller.save(request);
        assertEquals(response, result.getBody());
        verify(productCategoryService).save(request);
    }

    @Test
    void testGetAll() {
        List<ProductCategoryResponseDto> list = Arrays.asList(new ProductCategoryResponseDto());
        when(productCategoryService.getAll()).thenReturn(list);
        ResponseEntity<List<ProductCategoryResponseDto>> result = controller.getAll();
        assertEquals(list, result.getBody());
        verify(productCategoryService).getAll();
    }

    @Test
    void testUpdate() {
<<<<<<< HEAD
        Integer id = 1;
=======
        Long id = 1L;
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        ProductCategoryRequestDto request = new ProductCategoryRequestDto();
        ProductCategoryResponseDto response = new ProductCategoryResponseDto();
        when(productCategoryService.update(id, request)).thenReturn(response);
        ResponseEntity<ProductCategoryResponseDto> result = controller.update(id, request);
        assertEquals(response, result.getBody());
        verify(productCategoryService).update(id, request);
    }

    @Test
    void testDelete() {
<<<<<<< HEAD
        Integer id = 1;
=======
        Long id = 1L;
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        doNothing().when(productCategoryService).delete(id);
        ResponseEntity<Void> result = controller.delete(id);
        assertEquals(204, result.getStatusCodeValue());
        verify(productCategoryService).delete(id);
    }

    @Test
    void testUpdateDiscount() {
<<<<<<< HEAD
        BigInteger categoryId = BigInteger.valueOf(2);
        com.catalogservice.dto.DiscountDto discountDto = new com.catalogservice.dto.DiscountDto();
        discountDto.setDiscount(10.0F);
        List<ProductCategoryResponseDto> responseList = Arrays.asList(new ProductCategoryResponseDto());
        when(productCategoryService.updateDiscountByCategoryId(categoryId, discountDto.getDiscount())).thenReturn(responseList);
        ResponseEntity<List<ProductCategoryResponseDto>> result = controller.updateDiscount(categoryId, discountDto);
        assertEquals(responseList, result.getBody());
        verify(productCategoryService).updateDiscountByCategoryId(categoryId, discountDto.getDiscount());
=======
        Long categoryId = 2L;
        Double discount = 10.0;
        ProductCategoryResponseDto response = new ProductCategoryResponseDto();
        when(productCategoryService.updateDiscountByCategoryId(categoryId, discount)).thenReturn(response);
        ResponseEntity<ProductCategoryResponseDto> result = controller.updateDiscount(categoryId, discount);
        assertEquals(response, result.getBody());
        verify(productCategoryService).updateDiscountByCategoryId(categoryId, discount);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
    }

    @Test
    void testGetDiscountBySku_Found() {
        String sku = "SKU123";
<<<<<<< HEAD
        float discount = 10.0F;
        com.catalogservice.dto.ProductCategoryDiscountResponseDto response = new com.catalogservice.dto.ProductCategoryDiscountResponseDto(sku, (double) discount);
=======
        Double discount = 10.0;
        com.catalogservice.dto.ProductCategoryDiscountResponseDto response = new com.catalogservice.dto.ProductCategoryDiscountResponseDto(sku, discount);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        when(productCategoryService.getDiscountBySku(sku)).thenReturn(response);
        ResponseEntity<com.catalogservice.dto.ProductCategoryDiscountResponseDto> result = controller.getDiscountBySku(sku);
        assertEquals(response, result.getBody());
        assertEquals(200, result.getStatusCodeValue());
        verify(productCategoryService).getDiscountBySku(sku);
    }

    @Test
    void testGetDiscountBySku_NotFound() {
        String sku = "SKU_NOT_FOUND";
        when(productCategoryService.getDiscountBySku(sku)).thenReturn(null);
        ResponseEntity<com.catalogservice.dto.ProductCategoryDiscountResponseDto> result = controller.getDiscountBySku(sku);
        assertNull(result.getBody());
        assertEquals(404, result.getStatusCodeValue());
        verify(productCategoryService).getDiscountBySku(sku);
    }
}
