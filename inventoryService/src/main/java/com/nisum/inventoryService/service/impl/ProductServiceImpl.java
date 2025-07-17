package com.nisum.inventoryService.service.impl;

import com.nisum.inventoryService.dto.ProductAttributeDto;
import com.nisum.inventoryService.dto.ProductDto;
import com.nisum.inventoryService.dto.ProductFlatResponseDTO;
import com.nisum.inventoryService.dto.ProductFlatResponseDTO.ProductFlatItemDTO;
import com.nisum.inventoryService.dto.ProductResponseDto;
import com.nisum.inventoryService.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final RestTemplate restTemplate;

    @Autowired
    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProductResponseDto getAllProducts() {
        String url = "http://localhost:8000/products";

        try {
            ProductResponseDto response = restTemplate.getForObject(url, ProductResponseDto.class);
            return response != null ? response : new ProductResponseDto();
        } catch (Exception ex) {
            return new ProductResponseDto();
        }
    }

    @Override
    public ProductFlatResponseDTO getFlatProductList(int page, int size) {
        String url = "http://localhost:8000/products?page=" + page + "&size=" + size;

        ProductResponseDto responseDTO;
        try {
            responseDTO = restTemplate.getForObject(url, ProductResponseDto.class);
        } catch (Exception ex) {
            return new ProductFlatResponseDTO(); // return empty if failure
        }

        List<ProductFlatItemDTO> flatItemList = new ArrayList<>();

        if (responseDTO != null && responseDTO.getProducts() != null) {
            for (ProductDto product : responseDTO.getProducts()) {
                String status = product.getStatus();
                int categoryId = product.getCategoryId();

                for (ProductAttributeDto attr : product.getAttributes()) {
                    ProductFlatItemDTO flatItem = new ProductFlatItemDTO(status, attr.getSku(), categoryId);
                    flatItemList.add(flatItem);
                }
            }
        }

        ProductFlatResponseDTO flatResponse = new ProductFlatResponseDTO();
        flatResponse.setProducts(flatItemList);
        flatResponse.setCurrentPage(responseDTO.getCurrentPage());
        flatResponse.setPageSize(responseDTO.getPageSize());
        flatResponse.setTotalPages(responseDTO.getTotalPages());
        flatResponse.setTotalResults(responseDTO.getTotalResults());

        return flatResponse;
    }

}
