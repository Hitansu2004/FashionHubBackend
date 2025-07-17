package com.catalogservice.model;

import jakarta.persistence.*;

<<<<<<< HEAD
import java.math.BigInteger;

=======
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
@Entity
@Table(name = "product_categories")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
<<<<<<< HEAD
    @Column(name = "id") // Change this to your actual column name
    private Integer id;
    @Column(name = "category_id") // Change this to your actual column name
    private BigInteger categoryId;
    private String sku;
    private Integer price;
    private float discount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(BigInteger categoryId) {
=======
    private Long productCategoryId;
    private Long categoryId;
    private String sku;
    private Integer price;
    private Double discount;

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        this.categoryId = categoryId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

<<<<<<< HEAD
    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
=======
    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        this.discount = discount;
    }
}
