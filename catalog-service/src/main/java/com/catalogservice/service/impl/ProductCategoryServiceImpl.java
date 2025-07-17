package com.catalogservice.service.impl;

import com.catalogservice.dto.ProductCategoryRequestDto;
import com.catalogservice.dto.ProductCategoryResponseDto;
import com.catalogservice.dto.ProductCategoryDiscountResponseDto;
import com.catalogservice.mapper.ProductCategoryMapper;
<<<<<<< HEAD
import com.catalogservice.model.ProductCategory;
=======
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
import com.catalogservice.repository.ProductCategoryRepository;
import com.catalogservice.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import java.math.BigInteger;
=======
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository repo;
    private final ProductCategoryMapper mapper;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository repo, ProductCategoryMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public ProductCategoryResponseDto save(ProductCategoryRequestDto dto) {
        var entity = mapper.toEntity(dto);
        return mapper.toDto(repo.save(entity));
    }

    @Override
    public List<ProductCategoryResponseDto> getAll() {
        return repo.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
<<<<<<< HEAD
    public ProductCategoryResponseDto update(Integer id, ProductCategoryRequestDto dto) {
        var entity = repo.findById(id.longValue()).orElseThrow(
=======
    public ProductCategoryResponseDto update(Long id, ProductCategoryRequestDto dto) {
        var entity = repo.findById(id).orElseThrow(
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
                () -> new RuntimeException("ProductCategory not found with id: " + id)
        );
        mapper.updateEntity(entity, dto);
        return mapper.toDto(repo.save(entity));
    }

    @Override
<<<<<<< HEAD
    public void delete(Integer id) {
        if (!repo.existsById(id.longValue())) {
            throw new RuntimeException("ProductCategory not found with id: " + id);
        }
        repo.deleteById(id.longValue());
    }

    @Override
    public List<ProductCategoryResponseDto> updateDiscountByCategoryId(BigInteger categoryId, float discount) {
        List<ProductCategory> categories = repo.findAllByCategoryId(BigInteger.valueOf(categoryId.longValue()));
        if (categories.isEmpty()) {
            throw new RuntimeException("ProductCategory not found with categoryId: " + categoryId);
        }
        for (ProductCategory category : categories) {
            category.setDiscount(discount);
            repo.save(category);
        }
        return categories.stream().map(mapper::toDto).toList();
=======
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("ProductCategory not found with id: " + id);
        }
        repo.deleteById(id);
    }

    @Override
    public ProductCategoryResponseDto updateDiscountByCategoryId(Long categoryId, Double discount) {
        var entity = repo.findByCategoryId(categoryId)
            .orElseThrow(() -> new RuntimeException("ProductCategory not found with categoryId: " + categoryId));
        entity.setDiscount(discount);
        return mapper.toDto(repo.save(entity));
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
    }

    @Override
    public ProductCategoryDiscountResponseDto getDiscountBySku(String sku) {
        var entity = repo.findBySku(sku).orElseThrow(() -> new RuntimeException("ProductCategory not found for sku: " + sku));
<<<<<<< HEAD
        return new ProductCategoryDiscountResponseDto(entity.getSku(), (double) entity.getDiscount());
=======
        return new ProductCategoryDiscountResponseDto(entity.getSku(), entity.getDiscount());
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
    }
}
