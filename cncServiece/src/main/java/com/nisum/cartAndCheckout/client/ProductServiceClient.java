package com.nisum.cartAndCheckout.client;

import com.nisum.cartAndCheckout.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.nisum.cartAndCheckout.constants.AppConstants.*;

@Service
@RequiredArgsConstructor
public class ProductServiceClient {

    @Autowired
    private final RestTemplate restTemplate;

    public ProductWithAttributesDto getProductWithAttributesByProductIdAndSize(int productId, String size) {

        ResponseEntity<ProductWithAttributesDto> response = restTemplate.getForEntity(
                PRODUCT_WITH_ATTRIBUTE_URL, ProductWithAttributesDto.class, productId, size
        );
        System.out.println(response.getBody());
        return response.getBody(); // still returns ProductWithAttributesDto

    }


    public List<String> getAllAttributesByProductId(int productId) {

        ResponseEntity<String[]> response = restTemplate.getForEntity(
                PRODUCT_SIZES_URL, String[].class, productId
        );

        String[] arr = response.getBody();

        return arr != null ? Arrays.asList(arr) : Collections.emptyList();
    }


    public ProductCategoryDto getCategoryBySku(String sku) {

        ResponseEntity<ProductCategoryDto> response = restTemplate.getForEntity(
                CATALOG_PRODUCT_DISCOUNT_URL, ProductCategoryDto.class, sku
        );

        return response.getBody(); // Return type remains unchanged

    }

    public Integer getStockQuantityBySku(String sku) {
        ResponseEntity<String> response = restTemplate.getForEntity(
                INVENTORY_AVAILABLE_SKU_URL, String.class, sku
        );
        System.out.println("Raw stock response: " + response.getBody());
        return Optional.ofNullable(response.getBody())
                .map(body -> body.replaceAll(".*:\\s*", ""))
                .map(Integer::parseInt)
                .orElse(0);
    }

}