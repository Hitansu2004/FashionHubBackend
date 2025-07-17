package com.catalogservice.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ProductCategoryResponseDto {
    private Integer productCategoryId;  // Primary key
    private String sku;
    private BigInteger categoryId;
    private Integer price;
    private float discount;
    private Double discountedPrice;  // price - (price * discount/100)

    public Integer getProductCategoryId() { return productCategoryId; }
    public void setProductCategoryId(Integer productCategoryId) { this.productCategoryId = productCategoryId; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public BigInteger getCategoryId() { return categoryId; }
    public void setCategoryId(BigInteger categoryId) { this.categoryId = categoryId; }
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    public float getDiscount() { return discount; }
    public void setDiscount(float discount) { this.discount = discount; }

    public Double getDiscountedPrice() { return discountedPrice; }
    public void setDiscountedPrice(Double discountedPrice) { this.discountedPrice = discountedPrice; }
}
