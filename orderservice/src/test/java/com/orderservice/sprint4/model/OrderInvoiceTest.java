package com.orderservice.sprint4.model;

import com.orderservice.sprint4.model.enmus.PaymentMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderInvoiceTest {
    @Test
    void testInvoiceFields() {
        OrderInvoice invoice = new OrderInvoice();
        invoice.setInvoiceNumber("INV1234");
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setPaymentMode(PaymentMode.Card);
        invoice.setInvoiceAmount(BigDecimal.valueOf(500));

        Assertions.assertEquals("INV1234", invoice.getInvoiceNumber());
        Assertions.assertEquals(PaymentMode.Card, invoice.getPaymentMode());
    }
}
