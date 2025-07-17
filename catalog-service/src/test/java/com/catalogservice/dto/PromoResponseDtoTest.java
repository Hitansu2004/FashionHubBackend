package com.catalogservice.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PromoResponseDtoTest {
    @Test
    void testSettersAndGetters() {
        PromoResponseDto dto = new PromoResponseDto();
        dto.setPromoCode("PROMO1");
        dto.setPromoType("TYPE1");
        dto.setDescription("desc");
        dto.setAmount(100);
        dto.setStatus("ACTIVE");
        dto.setDiscount(10.0);
        assertEquals("PROMO1", dto.getPromoCode());
        assertEquals("TYPE1", dto.getPromoType());
        assertEquals("desc", dto.getDescription());
        assertEquals(100, dto.getAmount());
        assertEquals("ACTIVE", dto.getStatus());
        assertEquals(10.0, dto.getDiscount());
        dto.setDiscount(5); // Integer
        assertEquals(5, dto.getDiscount());
        dto.setDiscount("10 percent"); // String
        assertEquals("10 percent", dto.getDiscount());

    }

    @Test
    void testEqualsAndHashCode() {
        PromoResponseDto dto1 = new PromoResponseDto();
        PromoResponseDto dto2 = new PromoResponseDto();
        dto1.setPromoCode("PROMO1");
        dto2.setPromoCode("PROMO1");
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
