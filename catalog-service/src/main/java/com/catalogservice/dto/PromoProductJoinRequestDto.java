package com.catalogservice.dto;

import lombok.Data;

@Data
public class PromoProductJoinRequestDto {
    private Integer productId;
    private String promoCode;

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public String getPromoCode() { return promoCode; }
    public void setPromoCode(String promoCode) { this.promoCode = promoCode; }

}
