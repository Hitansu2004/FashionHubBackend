package com.orderservice.sprint4.controller;

import com.orderservice.sprint4.dto.ShipmentDetailsResponseDTO;
import com.orderservice.sprint4.service.InvoiceService;
import com.orderservice.sprint4.service.ShipmentService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ShipmentControllerTest {

    private ShipmentController shipmentController;
    private ShipmentService shipmentService;
    private InvoiceService invoiceService;

    @BeforeEach
    void setUp() {
        shipmentService = mock(ShipmentService.class);
        invoiceService = mock(InvoiceService.class);

        shipmentController = new ShipmentController();
        ReflectionTestUtils.setField(shipmentController, "shipmentService", shipmentService);
        ReflectionTestUtils.setField(shipmentController, "invoiceService", invoiceService);
    }


    @Test
    void testGenerateInvoicePdf_callsServiceMethod() throws IOException {
        // Arrange
        int orderId = 1;
        HttpServletResponse response = mock(HttpServletResponse.class);

        // Act
        shipmentController.generateInvoicePdf(orderId, response);

        // Assert
        verify(response).setContentType("application/pdf");
        verify(response).setHeader("content-Disposition", "attachment; filename=order_" + orderId + ".pdf");
        verify(invoiceService).generatepdf(orderId, response);
    }
}
