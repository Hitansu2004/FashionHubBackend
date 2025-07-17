package com.catalogservice.dto;

import lombok.Data;

@Data
public class PromoProductJoinRequestDto {
<<<<<<< HEAD
    private Integer productId;
    private String promoCode;

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public String getPromoCode() { return promoCode; }
    public void setPromoCode(String promoCode) { this.promoCode = promoCode; }
=======
    private String promoCode;        // FK to Promo.promo_code
    private Long productId;          // FK to Product.product_id

    public String getPromoCode() { return promoCode; }
    public void setPromoCode(String promoCode) { this.promoCode = promoCode; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
}
