package com.catalogservice.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PromoProductJoinResponseDtoTest {
    @Test
    void testSettersAndGetters() {
        PromoProductJoinResponseDto dto = new PromoProductJoinResponseDto();
        dto.setPromoCode("PROMO1");
<<<<<<< HEAD
        dto.setProductId(123);
        assertEquals("PROMO1", dto.getPromoCode());
        assertEquals(123, dto.getProductId());
=======
        dto.setProductId(123L);
        dto.setJoinId(1L);
        assertEquals("PROMO1", dto.getPromoCode());
        assertEquals(123L, dto.getProductId());
        assertEquals(1L, dto.getJoinId());
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
    }

    @Test
    void testEqualsAndHashCode() {
        PromoProductJoinResponseDto dto1 = new PromoProductJoinResponseDto();
        PromoProductJoinResponseDto dto2 = new PromoProductJoinResponseDto();
        dto1.setPromoCode("PROMO1");
<<<<<<< HEAD
        dto1.setProductId(123);
        dto2.setPromoCode("PROMO1");
        dto2.setProductId(123);
=======
        dto1.setProductId(123L);
        dto2.setPromoCode("PROMO1");
        dto2.setProductId(123L);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}
