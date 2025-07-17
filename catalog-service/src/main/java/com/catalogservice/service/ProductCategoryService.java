package com.catalogservice.service;

import com.catalogservice.dto.ProductCategoryRequestDto;
import com.catalogservice.dto.ProductCategoryResponseDto;
import com.catalogservice.dto.ProductCategoryDiscountResponseDto;
<<<<<<< HEAD
import jakarta.persistence.criteria.CriteriaBuilder;

import java.math.BigInteger;
=======

>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
import java.util.List;

public interface ProductCategoryService {
    ProductCategoryResponseDto save(ProductCategoryRequestDto dto);
    List<ProductCategoryResponseDto> getAll();
<<<<<<< HEAD
    ProductCategoryResponseDto update(Integer id, ProductCategoryRequestDto dto);
    void delete(Integer id);
    List<ProductCategoryResponseDto> updateDiscountByCategoryId(BigInteger categoryId, float discount);
=======
    ProductCategoryResponseDto update(Long id, ProductCategoryRequestDto dto);
    void delete(Long id);
    ProductCategoryResponseDto updateDiscountByCategoryId(Long categoryId, Double discount);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
    ProductCategoryDiscountResponseDto getDiscountBySku(String sku);
}
