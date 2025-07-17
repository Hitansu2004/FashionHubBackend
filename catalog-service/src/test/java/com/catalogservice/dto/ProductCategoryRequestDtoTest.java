package com.catalogservice.dto;

import org.junit.jupiter.api.Test;
<<<<<<< HEAD

import java.math.BigInteger;

=======
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryRequestDtoTest {
    @Test
    void testSettersAndGetters() {
        ProductCategoryRequestDto dto = new ProductCategoryRequestDto();
        dto.setSku("sku-123");
<<<<<<< HEAD
        dto.setCategoryId(BigInteger.valueOf(10));
        dto.setPrice(100);
        dto.setDiscount(5.5f);
        assertEquals("sku-123", dto.getSku());
        assertEquals(BigInteger.valueOf(10), dto.getCategoryId());
        assertEquals(100, dto.getPrice());
        assertEquals(5.5f, dto.getDiscount());
=======
        dto.setCategoryId(10L);
        dto.setPrice(500);
        dto.setDiscount(5.0);
        assertEquals("sku-123", dto.getSku());
        assertEquals(10L, dto.getCategoryId());
        assertEquals(500, dto.getPrice());
        assertEquals(5.0, dto.getDiscount());
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
    }

    @Test
    void testAllFields() {
        ProductCategoryRequestDto dto = new ProductCategoryRequestDto();
<<<<<<< HEAD
        dto.setSku("sku-1");
        dto.setCategoryId(BigInteger.valueOf(10));
        dto.setPrice(200);
        dto.setDiscount(10.0f);
        assertEquals("sku-1", dto.getSku());
        assertEquals(BigInteger.valueOf(10), dto.getCategoryId());
        assertEquals(200, dto.getPrice());
        assertEquals(10.0f, dto.getDiscount());
=======
        dto.setSku("sku-123");
        dto.setCategoryId(10L);
        dto.setPrice(500);
        dto.setDiscount(15.5);
        assertEquals("sku-123", dto.getSku());
        assertEquals(10L, dto.getCategoryId());
        assertEquals(500, dto.getPrice());
        assertEquals(15.5, dto.getDiscount());
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
    }

    @Test
    void testDefaultValues() {
        ProductCategoryRequestDto dto = new ProductCategoryRequestDto();
        assertNull(dto.getSku());
        assertNull(dto.getCategoryId());
        assertNull(dto.getPrice());
<<<<<<< HEAD
        assertEquals(0.0f, dto.getDiscount());
=======
        assertNull(dto.getDiscount());
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
    }

    @Test
    void testEqualsAndHashCode() {
        ProductCategoryRequestDto dto1 = new ProductCategoryRequestDto();
        ProductCategoryRequestDto dto2 = new ProductCategoryRequestDto();
        dto1.setSku("sku-1");
<<<<<<< HEAD
        dto1.setCategoryId(BigInteger.valueOf(2));
        dto1.setPrice(100);
        dto1.setDiscount(5.0F);
        dto2.setSku("sku-1");
        dto2.setCategoryId(BigInteger.valueOf(2));
        dto2.setPrice(100);
        dto2.setDiscount(5.0F);
=======
        dto1.setCategoryId(2L);
        dto1.setPrice(100);
        dto1.setDiscount(5.0);
        dto2.setSku("sku-1");
        dto2.setCategoryId(2L);
        dto2.setPrice(100);
        dto2.setDiscount(5.0);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        ProductCategoryRequestDto dto = new ProductCategoryRequestDto();
        dto.setSku("sku-1");
<<<<<<< HEAD
        dto.setCategoryId(BigInteger.valueOf(2L));
        dto.setPrice(100);
        dto.setDiscount(5.0F);
=======
        dto.setCategoryId(2L);
        dto.setPrice(100);
        dto.setDiscount(5.0);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        String str = dto.toString();
        assertTrue(str.contains("sku-1"));
        assertTrue(str.contains("ProductCategoryRequestDto"));
    }
}
