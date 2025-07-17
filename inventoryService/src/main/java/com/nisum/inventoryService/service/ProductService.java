package com.nisum.inventoryService.service;

import com.nisum.inventoryService.dto.ProductFlatResponseDTO;
import com.nisum.inventoryService.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto getAllProducts(); // Existing method
    ProductFlatResponseDTO getFlatProductList(int page, int size);

}
