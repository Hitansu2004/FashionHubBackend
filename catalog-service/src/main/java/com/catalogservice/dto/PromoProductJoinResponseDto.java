package com.catalogservice.dto;

import lombok.Data;

@Data
public class PromoProductJoinResponseDto {
<<<<<<< HEAD
    private Integer id;
    private String promoCode;
    private Integer productId;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getPromoCode() { return promoCode; }
    public void setPromoCode(String promoCode) { this.promoCode = promoCode; }
    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
=======
    private Long joinId;            // If you add a PK to Promotion_product_join
    private String promoCode;
    private Long productId;

    public Long getJoinId() { return joinId; }
    public void setJoinId(Long joinId) { this.joinId = joinId; }
    public String getPromoCode() { return promoCode; }
    public void setPromoCode(String promoCode) { this.promoCode = promoCode; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
}
