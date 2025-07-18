package com.orderservice.sprint4.model;

import com.orderservice.sprint4.model.enmus.OrderItemStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class OrderItemTest {
    @Test
    void testOrderItemFields() {
        OrderItem item = new OrderItem();
        item.setQuantity(2);
        item.setFinalPrice(BigDecimal.valueOf(300));
        item.setSize("M");
        item.setStatus(OrderItemStatus.Ordered);

        Assertions.assertEquals("M", item.getSize());
        Assertions.assertEquals(OrderItemStatus.Ordered, item.getStatus());
    }
}
