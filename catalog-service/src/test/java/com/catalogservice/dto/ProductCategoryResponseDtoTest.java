package com.catalogservice.dto;

import org.junit.jupiter.api.Test;
<<<<<<< HEAD

import java.math.BigInteger;

=======
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryResponseDtoTest {
    @Test
    void testSettersAndGetters() {
        ProductCategoryResponseDto dto = new ProductCategoryResponseDto();
<<<<<<< HEAD
        dto.setProductCategoryId(100);
        dto.setSku("sku-123");
        dto.setCategoryId(BigInteger.valueOf(200));
        dto.setPrice(500);
        dto.setDiscount(10.5F);
        dto.setDiscountedPrice(447.5);

        assertEquals(100, dto.getProductCategoryId());
        assertEquals("sku-123", dto.getSku());
        assertEquals(BigInteger.valueOf(200), dto.getCategoryId());
=======
        dto.setProductCategoryId(100L);
        dto.setSku("sku-123");
        dto.setCategoryId(200L);
        dto.setPrice(500);
        dto.setDiscount(10.5);
        dto.setDiscountedPrice(447.5);

        assertEquals(100L, dto.getProductCategoryId());
        assertEquals("sku-123", dto.getSku());
        assertEquals(200L, dto.getCategoryId());
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        assertEquals(500, dto.getPrice());
        assertEquals(10.5, dto.getDiscount());
        assertEquals(447.5, dto.getDiscountedPrice());
    }

    @Test
    void testDefaultValues() {
        ProductCategoryResponseDto dto = new ProductCategoryResponseDto();
        assertNull(dto.getProductCategoryId());
        assertNull(dto.getSku());
        assertNull(dto.getCategoryId());
        assertNull(dto.getPrice());
<<<<<<< HEAD
        assertNull(dto.getDiscountedPrice());
        assertEquals(0.0F, dto.getDiscount()); // discount defaults to 0.0F
=======
        assertNull(dto.getDiscount());
        assertNull(dto.getDiscountedPrice());
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
    }

    @Test
    void testEqualsAndHashCode() {
        ProductCategoryResponseDto dto1 = new ProductCategoryResponseDto();
        ProductCategoryResponseDto dto2 = new ProductCategoryResponseDto();
<<<<<<< HEAD
        dto1.setProductCategoryId(1);
        dto1.setSku("sku-1");
        dto1.setCategoryId(BigInteger.valueOf(2L));
        dto1.setPrice(100);
        dto1.setDiscount(5.0F);
        dto1.setDiscountedPrice(95.0);
        dto2.setProductCategoryId(1);
        dto2.setSku("sku-1");
        dto2.setCategoryId(BigInteger.valueOf(2L));
        dto2.setPrice(100);
        dto2.setDiscount(5.0F);
=======
        dto1.setProductCategoryId(1L);
        dto1.setSku("sku-1");
        dto1.setCategoryId(2L);
        dto1.setPrice(100);
        dto1.setDiscount(5.0);
        dto1.setDiscountedPrice(95.0);
        dto2.setProductCategoryId(1L);
        dto2.setSku("sku-1");
        dto2.setCategoryId(2L);
        dto2.setPrice(100);
        dto2.setDiscount(5.0);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        dto2.setDiscountedPrice(95.0);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testToString() {
        ProductCategoryResponseDto dto = new ProductCategoryResponseDto();
<<<<<<< HEAD
        dto.setProductCategoryId(1);
        dto.setSku("sku-1");
        dto.setCategoryId(BigInteger.valueOf(2L));
        dto.setPrice(100);
        dto.setDiscount(5.0F);
=======
        dto.setProductCategoryId(1L);
        dto.setSku("sku-1");
        dto.setCategoryId(2L);
        dto.setPrice(100);
        dto.setDiscount(5.0);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        dto.setDiscountedPrice(95.0);
        String str = dto.toString();
        assertTrue(str.contains("sku-1"));
        assertTrue(str.contains("ProductCategoryResponseDto"));
    }
}
