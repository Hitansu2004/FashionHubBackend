package com.catalogservice.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PromoProductJoinRequestDtoTest {
    @Test
    void testSettersAndGetters() {
        PromoProductJoinRequestDto dto = new PromoProductJoinRequestDto();
        dto.setPromoCode("PROMO1");
<<<<<<< HEAD
        dto.setProductId(123);
        assertEquals("PROMO1", dto.getPromoCode());
        assertEquals(123, dto.getProductId());
=======
        dto.setProductId(123L);
        assertEquals("PROMO1", dto.getPromoCode());
        assertEquals(123L, dto.getProductId());
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
    }
}
