package com.catalogservice.service;

import com.catalogservice.dto.ProductCategoryRequestDto;
import com.catalogservice.dto.ProductCategoryResponseDto;
import com.catalogservice.dto.ProductCategoryDiscountResponseDto;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.math.BigInteger;

import java.util.List;

public interface ProductCategoryService {
    ProductCategoryResponseDto save(ProductCategoryRequestDto dto);

    List<ProductCategoryResponseDto> getAll();

    ProductCategoryResponseDto update(Integer id, ProductCategoryRequestDto dto);

    void delete(Integer id);

    List<ProductCategoryResponseDto> updateDiscountByCategoryId(BigInteger categoryId, float discount);

    ProductCategoryDiscountResponseDto getDiscountBySku(String sku);
}
