package com.catalogservice.model;

import jakarta.persistence.Column;

import java.io.Serializable;

public class PromoProductJoinId implements Serializable {
    @Column(name = "id")
    private Integer joinId;
    private Integer productId;
    private String promoCode;

    public Integer getJoinId() {
        return joinId;
    }

    public void setJoinId(Integer joinId) {
        this.joinId = joinId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {

        this.productId = productId;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromoProductJoinId that = (PromoProductJoinId) o;
        return java.util.Objects.equals(joinId, that.joinId) &&
                java.util.Objects.equals(productId, that.productId) &&

                java.util.Objects.equals(promoCode, that.promoCode);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(joinId, productId, promoCode);

    }
}
