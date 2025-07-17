package com.catalogservice.dto;

import lombok.Data;

<<<<<<< HEAD
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
=======
@Data
public class ProductCategoryRequestDto {
    private String sku;          // SKU of the product
    private Long categoryId;     // Foreign key to category
    private Integer price;       // Product price
    private Double discount;     // Discount percentage

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    public Double getDiscount() { return discount; }
    public void setDiscount(Double discount) { this.discount = discount; }
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
}
