package com.catalogservice.service.impl;

import com.catalogservice.dto.ProductCategoryRequestDto;
import com.catalogservice.dto.ProductCategoryResponseDto;
import com.catalogservice.mapper.ProductCategoryMapper;
import com.catalogservice.model.ProductCategory;
import com.catalogservice.repository.ProductCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

<<<<<<< HEAD
import java.math.BigInteger;
=======
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductCategoryServiceImplTest {
    @Mock
    private ProductCategoryRepository repository;
    @Mock
    private ProductCategoryMapper mapper;
    @InjectMocks
    private ProductCategoryServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        ProductCategoryRequestDto request = new ProductCategoryRequestDto();
        ProductCategory entity = new ProductCategory();
        ProductCategoryResponseDto response = new ProductCategoryResponseDto();
        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(response);
        ProductCategoryResponseDto result = service.save(request);
        assertEquals(response, result);
    }

    @Test
    void testGetAll() {
        List<ProductCategory> entities = Arrays.asList(new ProductCategory());
        List<ProductCategoryResponseDto> dtos = Arrays.asList(new ProductCategoryResponseDto());
        when(repository.findAll()).thenReturn(entities);
        when(mapper.toDto(any(ProductCategory.class))).thenReturn(dtos.get(0));
        List<ProductCategoryResponseDto> result = service.getAll();
        assertEquals(dtos.size(), result.size());
    }

    @Test
    void testUpdate() {
<<<<<<< HEAD
        Integer id = 1;
        ProductCategoryRequestDto request = new ProductCategoryRequestDto();
        ProductCategory entity = new ProductCategory();
        ProductCategoryResponseDto response = new ProductCategoryResponseDto();
        when(repository.findById(Long.valueOf(id))).thenReturn(Optional.of(entity));
=======
        Long id = 1L;
        ProductCategoryRequestDto request = new ProductCategoryRequestDto();
        ProductCategory entity = new ProductCategory();
        ProductCategoryResponseDto response = new ProductCategoryResponseDto();
        when(repository.findById(id)).thenReturn(Optional.of(entity));
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        doNothing().when(mapper).updateEntity(entity, request);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(response);
        ProductCategoryResponseDto result = service.update(id, request);
        assertEquals(response, result);
    }

    @Test
    void testDelete() {
<<<<<<< HEAD
        Integer id = 1;
        when(repository.existsById(Long.valueOf(id))).thenReturn(true);
        doNothing().when(repository).deleteById(Long.valueOf(id));
        service.delete(id);
        verify(repository).deleteById(Long.valueOf(id));
=======
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);
        service.delete(id);
        verify(repository).deleteById(id);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
    }

    @Test
    void testUpdate_NotFound() {
<<<<<<< HEAD
        Integer id = 99;
        ProductCategoryRequestDto request = new ProductCategoryRequestDto();
        when(repository.findById(Long.valueOf(id))).thenReturn(Optional.empty());
=======
        Long id = 99L;
        ProductCategoryRequestDto request = new ProductCategoryRequestDto();
        when(repository.findById(id)).thenReturn(Optional.empty());
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.update(id, request));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void testDelete_NotFound() {
<<<<<<< HEAD
        Integer id = 99;
        when(repository.existsById(Long.valueOf(id))).thenReturn(false);
=======
        Long id = 99L;
        when(repository.existsById(id)).thenReturn(false);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.delete(id));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void testUpdateDiscountByCategoryId() {
<<<<<<< HEAD
        BigInteger categoryId = BigInteger.valueOf(2);
        float discount = 10.0F;
        ProductCategory entity = new ProductCategory();
        ProductCategoryResponseDto response = new ProductCategoryResponseDto();
        List<ProductCategory> entities = List.of(entity);
        when(repository.findAllByCategoryId(categoryId)).thenReturn(entities);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(response);
        List<ProductCategoryResponseDto> result = service.updateDiscountByCategoryId(categoryId, discount);
        assertEquals(List.of(response), result);
=======
        Long categoryId = 2L;
        Double discount = 10.0;
        ProductCategory entity = new ProductCategory();
        ProductCategoryResponseDto response = new ProductCategoryResponseDto();
        when(repository.findByCategoryId(categoryId)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(response);
        ProductCategoryResponseDto result = service.updateDiscountByCategoryId(categoryId, discount);
        assertEquals(response, result);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
    }

    @Test
    void testUpdateDiscountByCategoryId_NotFound() {
<<<<<<< HEAD
        BigInteger categoryId = BigInteger.valueOf(99);
        float discount = 10.0F;
        when(repository.findAllByCategoryId(categoryId)).thenReturn(List.of());
=======
        Long categoryId = 99L;
        Double discount = 10.0;
        when(repository.findByCategoryId(categoryId)).thenReturn(Optional.empty());
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.updateDiscountByCategoryId(categoryId, discount));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void testGetDiscountBySku() {
        String sku = "SKU123";
        ProductCategory entity = new ProductCategory();
        entity.setSku(sku);
<<<<<<< HEAD
        entity.setDiscount(15.0F);
=======
        entity.setDiscount(15.0);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        when(repository.findBySku(sku)).thenReturn(Optional.of(entity));
        var result = service.getDiscountBySku(sku);
        assertEquals(sku, result.getSku());
        assertEquals(15.0, result.getDiscount());
    }

    @Test
    void testGetDiscountBySku_NotFound() {
        String sku = "SKU_NOT_FOUND";
        when(repository.findBySku(sku)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.getDiscountBySku(sku));
        assertTrue(ex.getMessage().contains("not found"));
    }
}
