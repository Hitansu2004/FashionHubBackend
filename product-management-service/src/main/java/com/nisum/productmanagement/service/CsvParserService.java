package com.nisum.productmanagement.service;

import com.nisum.productmanagement.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvParserService {

    public List<Product> parseCsv(MultipartFile file) {
        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"))) {
            String header = reader.readLine();
            
            if (header == null || header.trim().isEmpty()) {
                throw new IllegalArgumentException("CSV file is empty");
            }
            
            String delimiter = header.contains(",") ? "," : "\t";
            String[] headerColumns = header.split(delimiter);
            
            if (headerColumns.length < 4) {
                throw new IllegalArgumentException("CSV must have 4 columns: name, category, lastModified, seller");
            }
            
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                Product product = parseProductLine(line, delimiter);
                if (product != null) {
                    products.add(product);
                }
            }
            
            if (products.isEmpty()) {
                throw new IllegalArgumentException("No valid product data found");
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file: " + e.getMessage());
        }

        return products;
    }
    private Product parseProductLine(String line, String delimiter) {
        String[] fields = line.split(delimiter, -1);
        
        if (fields.length < 4) {
            throw new IllegalArgumentException("Line must have 4 columns");
        }
        
        Product product = new Product();
        
        // Set basic fields
        product.setName(fields[0].trim());
        product.setCategoryName(fields[1].trim());
        product.setLastModifiedDate(parseDateTime(fields[2].trim()));
        
        // Handle seller (ID or name)
        String sellerField = fields[3].trim();
        try {
            product.setSellerId(Long.parseLong(sellerField));
        } catch (NumberFormatException e) {
            product.setSellerName(sellerField);
        }
        
        product.setStatus("Inactive"); // Default status, can be changed later
        return product;
    }
    
    private LocalDateTime parseDateTime(String dateString) {
        if (dateString.isEmpty()) {
            return LocalDateTime.now();
        }
        
        String[] formats = {
            "dd-MM-yyyy HH:mm",
            "yyyy-MM-dd HH:mm",
            "dd-MM-yyyy",
            "yyyy-MM-dd"
        };
        
        for (String format : formats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                if (format.contains("HH:mm")) {
                    return LocalDateTime.parse(dateString, formatter);
                } else {
                    return LocalDateTime.parse(dateString + " 00:00:00", 
                                             DateTimeFormatter.ofPattern(format + " HH:mm:ss"));
                }
            } catch (DateTimeParseException ignored) {
            }
        }
        
        return LocalDateTime.now();
    }
}
