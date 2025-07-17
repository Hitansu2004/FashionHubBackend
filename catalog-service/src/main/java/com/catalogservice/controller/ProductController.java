package com.catalogservice.controller;

import com.catalogservice.dto.ProductCategoryRequestDto;
import com.catalogservice.service.ProductCategoryService;
import com.catalogservice.service.PromoProductJoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private PromoProductJoinService promoProductJoinService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/ids")
    public ResponseEntity<List<Integer>> getAllProductIds() {
        // Fetch product IDs from another service or source
        List<Integer> productIds = promoProductJoinService.getAllProductIds();
        // Store product IDs in PromoProductJoin
        promoProductJoinService.storeProductIds(productIds);
        return ResponseEntity.ok(productIds);
    }

    @GetMapping
    public ResponseEntity<String> fetchAndStoreProducts() {
        RestTemplate restTemplate = new RestTemplate();
        String otherTeamUrl = "http://localhost:8083/products";
        ResponseEntity<List> response = restTemplate.exchange(
                otherTeamUrl,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                List.class
        );
        List<Map<String, Object>> products = response.getBody();
        if (products == null) return ResponseEntity.badRequest().body("No products found");
        int storedCount = 0;
        for (Map<String, Object> product : products) {
            Object categoryId = product.get("category_id");
            Object price = product.get("price");
            Object sku = product.get("sku");
            if (categoryId != null && price != null && sku != null) {
                ProductCategoryRequestDto dto = new ProductCategoryRequestDto();
                dto.setCategoryId(BigInteger.valueOf(Long.parseLong(categoryId.toString())));
                dto.setPrice(Integer.valueOf(price.toString()));
                dto.setSku(sku.toString());
                productCategoryService.save(dto);
                storedCount++;
            }
        }
        return ResponseEntity.ok("Stored " + storedCount + " products in productcategories table.");
    }
}
