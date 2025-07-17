package com.catalogservice.dto;

import lombok.Data;

@Data
public class PromoProductJoinResponseDto {
    private Integer id;
    private String promoCode;
    private Integer productId;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getPromoCode() { return promoCode; }
    public void setPromoCode(String promoCode) { this.promoCode = promoCode; }
    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

}
