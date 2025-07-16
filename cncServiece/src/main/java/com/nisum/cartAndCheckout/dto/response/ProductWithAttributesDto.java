package com.nisum.cartAndCheckout.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithAttributesDto {
    private int id;
    private String name;
    private int categoryId;
    private String categoryName;
    private String status;
    private LocalDateTime lastModifiedDate;
    private int sellerId;

    private List<ProductAttributesDto> attributes;
}