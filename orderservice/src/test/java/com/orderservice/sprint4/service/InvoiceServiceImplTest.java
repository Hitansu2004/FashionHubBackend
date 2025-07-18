package com.orderservice.sprint4.service;

import com.itextpdf.text.DocumentException;
import com.orderservice.sprint4.exception.PdfGenerationException;
import com.orderservice.sprint4.model.Order;
import com.orderservice.sprint4.model.OrderInvoice;
import com.orderservice.sprint4.model.OrderItem;
import com.orderservice.sprint4.model.enmus.OrderStatus;
import com.orderservice.sprint4.model.enmus.PaymentMode;
import com.orderservice.sprint4.repository.OrderInvoiceRepository;
import com.orderservice.sprint4.repository.OrderItemRepository;
import com.orderservice.sprint4.repository.OrderRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvoiceServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderInvoiceRepository orderInvoiceRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGeneratePdf_Success() throws Exception {
        // Setup order and related data
        OrderInvoice invoice = new OrderInvoice();
        invoice.setInvoiceNumber("INV-1234");
        invoice.setInvoiceDate(LocalDateTime.of(2025, 7, 15, 10, 0));
        invoice.setPaymentMode(PaymentMode.Card);
        invoice.setInvoiceAmount(new BigDecimal("300.00"));

        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(1);
        orderItem.setQuantity(2);
        orderItem.setUnitPrice(new BigDecimal("100.00"));
        orderItem.setDiscount(new BigDecimal("10.00"));
        orderItem.setFinalPrice(new BigDecimal("190.00"));

        Order order = new Order();
        order.setOrderId(10);
        order.setOrderDate(LocalDateTime.of(2025, 7, 10, 9, 0));
        order.setOrderStatus(OrderStatus.Ordered);
        order.setPromoDiscount(new BigDecimal("20.00"));
        order.setOrderInvoice(invoice);
        order.setOrderItems(Collections.singletonList(orderItem));

        when(orderRepository.findById(10)).thenReturn(Optional.of(order));

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        // Mock ServletOutputStream to write to ByteArrayOutputStream
        ServletOutputStream mockStream = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                byteStream.write(b);
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }
        };

        when(response.getOutputStream()).thenReturn(mockStream);

        // Act & Assert
        assertDoesNotThrow(() -> invoiceService.generatepdf(10, response));
        assertTrue(byteStream.size() > 0);
        verify(orderRepository).findById(10);
    }

    @Test
    void testGeneratePdf_OrderNotFound_ShouldThrowException() {
        when(orderRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                invoiceService.generatepdf(99, response));

        assertEquals("Order not found", ex.getMessage());
    }

    @Test
    void testGeneratePdf_OutputStreamFails_ShouldThrowPdfGenerationException() throws Exception {
        Order order = new Order();
        order.setOrderId(10);
        order.setOrderInvoice(new OrderInvoice());
        order.setOrderItems(Collections.emptyList());

        when(orderRepository.findById(10)).thenReturn(Optional.of(order));
        when(response.getOutputStream()).thenThrow(new IOException("I/O Error"));

        PdfGenerationException ex = assertThrows(PdfGenerationException.class, () ->
                invoiceService.generatepdf(10, response));

        assertTrue(ex.getMessage().contains("Failed to generate invoice PDF"));
        verify(orderRepository).findById(10);
    }
}
