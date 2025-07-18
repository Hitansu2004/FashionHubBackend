package com.orderservice.sprint4.service;

import com.orderservice.sprint4.dao.ShipmentItemDAO;
import com.orderservice.sprint4.dto.InvoiceResponseDTO;
import com.orderservice.sprint4.dto.ProductDTO;
import com.orderservice.sprint4.dto.SellerDTO;
import com.orderservice.sprint4.dto.ShipmentDetailsResponseDTO;
import com.orderservice.sprint4.exception.InvoiceNotFoundException;
import com.orderservice.sprint4.exception.OrderNotFoundException;
import com.orderservice.sprint4.exception.ShipmentItemsNotFoundException;
import com.orderservice.sprint4.model.Order;
import com.orderservice.sprint4.model.enmus.OrderStatus;
import com.orderservice.sprint4.model.enmus.PaymentMode;
import com.orderservice.sprint4.model.enmus.ShipmentItemStatus;
import com.orderservice.sprint4.repository.OrderInvoiceRepository;
import com.orderservice.sprint4.repository.OrderRepository;
import com.orderservice.sprint4.repository.custom.CustomShipmentItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShipmentServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderInvoiceRepository orderInvoiceRepository;

    @Mock
    private CustomShipmentItemRepository customShipmentItemRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ShipmentServiceImpl shipmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(shipmentService, "PRODUCT_SERVICE_PRODUCT_DATA_URL", "http://product-service/product/");
        ReflectionTestUtils.setField(shipmentService, "PRODUCT_SERVICE_SELLER_DATA_URL", "http://product-service/seller/");
    }

    @Test
    void getShipmentItemsByOrderId_success() {
        Integer orderId = 1;

        Order order = new Order();
        order.setOrderId(orderId);
        order.setOrderStatus(OrderStatus.Ordered);
        order.setOrderDate(LocalDateTime.now());

        ShipmentItemDAO itemDAO = new ShipmentItemDAO();
        itemDAO.setProduct(100);
        itemDAO.setQuantity(2);
        itemDAO.setSku("SKU123");
        itemDAO.setTrackingId("TRK123");
        itemDAO.setItemStatus(ShipmentItemStatus.InTransit);
        itemDAO.setShipmentDate(LocalDateTime.now());
        itemDAO.setDeliveredDate(null);

        InvoiceResponseDTO invoice = new InvoiceResponseDTO();
        invoice.setPaymentMode(PaymentMode.UPI);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");

        SellerDTO sellerDTO = new SellerDTO();
        sellerDTO.setSellerName("Test Seller");

        // Mock repository and rest calls
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(customShipmentItemRepository.getShipmentItemsByOrderId(orderId)).thenReturn(List.of(itemDAO));
        when(orderInvoiceRepository.getInvoiceByOrderId(orderId)).thenReturn(invoice);

        when(restTemplate.getForEntity("http://product-service/product/100", ProductDTO.class))
                .thenReturn(ResponseEntity.ok(productDTO));
        when(restTemplate.getForEntity("http://product-service/seller/100/sellers", SellerDTO.class))
                .thenReturn(ResponseEntity.ok(sellerDTO));

        // Execute
        ShipmentDetailsResponseDTO result = shipmentService.getShipmentItemsByOrderId(orderId);

        // Verify
        assertNotNull(result);
        assertEquals(orderId, result.getOrderId());
        assertEquals(PaymentMode.UPI, result.getPaymentMode());
        assertEquals(1, result.getItems().size());
        assertEquals("Test Product", result.getItems().get(0).getProduct());
        assertEquals("Test Seller", result.getItems().get(0).getSeller());
    }

    @Test
    void getShipmentItemsByOrderId_orderNotFound() {
        Integer orderId = 1;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> shipmentService.getShipmentItemsByOrderId(orderId));
    }

    @Test
    void getShipmentItemsByOrderId_shipmentItemsNotFound() {
        Integer orderId = 1;
        Order order = new Order();
        order.setOrderId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(customShipmentItemRepository.getShipmentItemsByOrderId(orderId)).thenReturn(Collections.emptyList());
        assertThrows(ShipmentItemsNotFoundException.class, () -> shipmentService.getShipmentItemsByOrderId(orderId));
    }



}
