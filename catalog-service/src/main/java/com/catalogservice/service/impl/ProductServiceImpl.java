package com.catalogservice.service.impl;

import com.catalogservice.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public List<Integer> getAllProductIds() {
        // Dummy implementation, replace with actual logic if needed
        return new ArrayList<>();
    }
}

