package com.catalogservice.mapper;

import com.catalogservice.dto.ProductCategoryRequestDto;
import com.catalogservice.dto.ProductCategoryResponseDto;
import com.catalogservice.model.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryMapper {

    public ProductCategory toEntity(ProductCategoryRequestDto dto) {
        ProductCategory entity = new ProductCategory();
        entity.setSku(dto.getSku());
        entity.setCategoryId(dto.getCategoryId());
        entity.setPrice(dto.getPrice());
        entity.setDiscount(dto.getDiscount());
        return entity;
    }

    public ProductCategoryResponseDto toDto(ProductCategory entity) {
        ProductCategoryResponseDto dto = new ProductCategoryResponseDto();
        dto.setProductCategoryId(entity.getId());

        dto.setSku(entity.getSku());
        dto.setCategoryId(entity.getCategoryId());
        dto.setPrice(entity.getPrice());
        dto.setDiscount(entity.getDiscount());
        // Null checks for price and discount
        if (entity.getPrice() != null && entity.getDiscount() != 0.0F) {
            dto.setDiscountedPrice(
                    (double) (entity.getPrice() - (entity.getPrice() * entity.getDiscount() / 100))

            );
        } else {
            dto.setDiscountedPrice(null);
        }
        return dto;
    }

    public void updateEntity(ProductCategory entity, ProductCategoryRequestDto dto) {
        if (dto.getSku() != null) {
            entity.setSku(dto.getSku());
        }
        if (dto.getCategoryId() != null) {
            entity.setCategoryId(dto.getCategoryId());
        }
        if (dto.getPrice() != null) {
            entity.setPrice(dto.getPrice());
        }
        // Fix for primitive types: check for default value instead of null
        if (dto.getDiscount() != 0) {

            entity.setDiscount(dto.getDiscount());
        }
    }
}
