package com.catalogservice.dto;

import lombok.Data;

<<<<<<< HEAD
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
=======
@Data
public class ProductCategoryResponseDto {
    private Long productCategoryId;  // Primary key
    private String sku;
    private Long categoryId;
    private Integer price;
    private Double discount;
    private Double discountedPrice;  // price - (price * discount/100)

    public Long getProductCategoryId() { return productCategoryId; }
    public void setProductCategoryId(Long productCategoryId) { this.productCategoryId = productCategoryId; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    public Double getDiscount() { return discount; }
    public void setDiscount(Double discount) { this.discount = discount; }
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
    public Double getDiscountedPrice() { return discountedPrice; }
    public void setDiscountedPrice(Double discountedPrice) { this.discountedPrice = discountedPrice; }
}
