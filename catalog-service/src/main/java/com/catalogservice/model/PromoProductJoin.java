package com.catalogservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Promotion_product_join")
public class PromoProductJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "promo_code")
    private String promoCode;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public String getPromoCode() { return promoCode; }
    public void setPromoCode(String promoCode) { this.promoCode = promoCode; }

}
