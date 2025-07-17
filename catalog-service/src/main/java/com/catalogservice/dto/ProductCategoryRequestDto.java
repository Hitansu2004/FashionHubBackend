package com.catalogservice.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ProductCategoryRequestDto {
    private String sku;          // SKU of the product
    private BigInteger categoryId;     // Foreign key to category
    private Integer price;       // Product price
    private float discount;     // Discount percentage

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public BigInteger getCategoryId() { return categoryId; }
    public void setCategoryId(BigInteger categoryId) { this.categoryId = categoryId; }
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    public float getDiscount() { return discount; }
    public void setDiscount(float discount) { this.discount = discount; }

}
