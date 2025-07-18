package com.orderservice.sprint4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderservice.sprint4.dto.*;
import com.orderservice.sprint4.model.enmus.OrderItemStatus;
import com.orderservice.sprint4.model.enmus.OrderStatus;
import com.orderservice.sprint4.model.enmus.PaymentMode;
import com.orderservice.sprint4.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Optional alternative for better isolation
// @WebMvcTest(OrderController.class)
class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void createOrder_success() throws Exception {
        // Prepare response DTO
        Map<String, String> orderItems = new HashMap<>();
        orderItems.put("item123", "Ordered");
        OrderResponseDTO mockResponse = OrderResponseDTO.builder()
                .orderItemIds(orderItems)
                .status("Ordered").build();

        // Prepare request DTO
        OrderDetailsRequestDTO requestDTO = new OrderDetailsRequestDTO();
        requestDTO.setUserId(1);
        requestDTO.setOrderStatus(OrderStatus.Ordered);
        requestDTO.setPromoDiscount(BigDecimal.valueOf(100));
        requestDTO.setOrderTotal(BigDecimal.valueOf(1400));
        requestDTO.setPaymentMode(PaymentMode.Card);
        requestDTO.setAddressId(101);
        requestDTO.setOrderItemRequestDTOS(List.of()); // Empty list is fine

        // Mock service behavior
        when(orderService.createOrderTransaction(any(OrderDetailsRequestDTO.class), eq("dummyToken")))
                .thenReturn(mockResponse);

        // Perform request
        mockMvc.perform(post("/orders/create")
                        .header("Authorization", "Bearer dummyToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Ordered"))
                .andExpect(jsonPath("$.orderItemIds.item123").value("Ordered"));
    }






    @Test
    void createOrder_failure() throws Exception {
        OrderDetailsRequestDTO dto = new OrderDetailsRequestDTO();

        when(orderService.createOrderTransaction(any(),anyString()))
                .thenThrow(new RuntimeException("DB error"));

        mockMvc.perform(post("/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.status").value("failure"))
                .andExpect(jsonPath("$.orderItemIds").isMap());
    }

    @Test
    void getOrderDetails_success() throws Exception {
        OrderDetailsResponseDTO responseDTO = new OrderDetailsResponseDTO();
        responseDTO.setOrderId(1);
        responseDTO.setOrderStatus(OrderStatus.Ordered);  // Set enum value

        when(orderService.getOrderDetails(eq(1), eq("dummyToken"))).thenReturn(responseDTO);

        mockMvc.perform(get("/orders/1")
                        .header("Authorization", "Bearer dummyToken"))  // ✅ Fix here
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.orderStatus").value("Ordered")); // Match enum string
    }



    @Test
    void getOrderDetails_notFound() throws Exception {
        when(orderService.getOrderDetails(any(),anyString())).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    void getOrdersList_success() throws Exception {
        OrderSummaryDTO dto = new OrderSummaryDTO();
        dto.setOrderId(101);
        dto.setOrderStatus(OrderStatus.Ordered);
        dto.setOrderTotal(BigDecimal.valueOf(1500.00));

        List<OrderSummaryDTO> mockOrders = Collections.singletonList(dto);

        when(orderService.getOrders(eq(7), eq("dummyToken"))).thenReturn(mockOrders);

        mockMvc.perform(get("/orders/list/7")
                        .header("Authorization", "Bearer dummyToken"))  // ✅ Correct header
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].orderId").value(101))
                .andExpect(jsonPath("$[0].orderStatus").value("Ordered"))
                .andExpect(jsonPath("$[0].orderTotal").value(1500.00));
    }




    @Test
    void getOrdersList_failure() throws Exception {
        when(orderService.getOrders(any(),anyString())).thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(get("/orders/list/7"))
                .andExpect(status().isNotFound());
    }

    @Test
    void orderConfirmation_success() throws Exception {
        OrderStatusRequestDTO dto = new OrderStatusRequestDTO(); // populate if needed

        doNothing().when(orderService).orderConfirm(any());

        mockMvc.perform(patch("/orders/order/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }



}
