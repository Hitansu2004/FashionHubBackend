
package com.orderservice.sprint4.service;

import com.orderservice.sprint4.dto.*;
import com.orderservice.sprint4.exception.OrderNotFoundException;
import com.orderservice.sprint4.model.*;
import com.orderservice.sprint4.model.enmus.OrderItemStatus;
import com.orderservice.sprint4.model.enmus.OrderStatus;
import com.orderservice.sprint4.model.enmus.PaymentMode;
import com.orderservice.sprint4.repository.*;
import com.orderservice.sprint4.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private JavaMailSender mailSender;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private OrderInvoiceRepository orderInvoiceRepository;
    @Mock
    private ShipmentItemRepository shipmentItemRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetOrderDetails_Unauthorized() {
        String token = "dummyToken";
        Integer orderId = 1;

        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId(200); // Mismatched user ID

        when(jwtUtil.getUserIdFromToken(token)).thenReturn(100);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        assertThrows(RuntimeException.class, () -> orderService.getOrderDetails(orderId, token));
    }


    @Test
    void testGetOrderDetails_Success() {
        Order order = new Order();
        order.setOrderId(1);
        order.setUserId(101);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.Ordered);
        order.setPromoDiscount(BigDecimal.valueOf(10.0));
        order.setOrderTotal(BigDecimal.valueOf(1000.0));
        order.setOrderItems(new ArrayList<>());

        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(jwtUtil.getUserIdFromToken("valid_token")).thenReturn(101);

        OrderDetailsResponseDTO response = orderService.getOrderDetails(1, "valid_token");

        assertNotNull(response);
        assertEquals(1, response.getOrderId());
        assertEquals(101, response.getUserId());
    }

    @Test
    void testGetOrderDetails_UnauthorizedAccess() {
        Order order = new Order();
        order.setOrderId(1);
        order.setUserId(101);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(jwtUtil.getUserIdFromToken("invalid_token")).thenReturn(999);

        assertThrows(Exception.class, () -> {
            orderService.getOrderDetails(1, "invalid_token");
        });
    }

    @Test
    void testGetOrderDetails_OrderNotFound() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            orderService.getOrderDetails(1, "token");
        });
    }



    @Test
    void testOrderConfirm_InvalidStatus() {
        OrderStatusRequestDTO dto = new OrderStatusRequestDTO();
        dto.setOrderStatus(null);

        assertThrows(RuntimeException.class, () -> {
            orderService.orderConfirm(dto);
        });
    }

}
