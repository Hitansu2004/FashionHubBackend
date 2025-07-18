package com.orderservice.sprint4.model;

import com.orderservice.sprint4.model.enmus.OrderReturnStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderReturnTest {
    @Test
    void testOrderReturnFields() {
        OrderReturn ret = new OrderReturn();
        ret.setReturnReason("Size mismatch");
        ret.setReturnStatus(OrderReturnStatus.Requested);
        ret.setReturnDate(LocalDateTime.now());
        ret.setRefundAmount(BigDecimal.valueOf(150));

        Assertions.assertEquals("Size mismatch", ret.getReturnReason());
        Assertions.assertEquals(OrderReturnStatus.Requested, ret.getReturnStatus());
    }
}
