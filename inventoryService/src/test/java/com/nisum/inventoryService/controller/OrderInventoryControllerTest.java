package com.nisum.inventoryService.controller;

import com.nisum.inventoryService.dto.AllocateInventoryDTO;
import com.nisum.inventoryService.dto.OrderInventoryDTO;
import com.nisum.inventoryService.dto.ReserveRequest;
import com.nisum.inventoryService.service.InventoryService;
import com.nisum.inventoryService.service.OrderInventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderInventoryControllerTest {

    @InjectMocks
    private OrderInventoryController controller;

    @Mock
    private OrderInventoryService orderService;

    @Mock
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Helper to build OrderInventoryDTO
    private OrderInventoryDTO buildOrderDTO(int id) {
        OrderInventoryDTO dto = new OrderInventoryDTO();
        dto.setOrderId(id);
        return dto;
    }

    // POST /api/orders
    @Test
    void testCreateOrder_Positive() {
        OrderInventoryDTO dto = buildOrderDTO(1);
        when(orderService.createOrder(dto)).thenReturn(dto);

        ResponseEntity<OrderInventoryDTO> response = controller.createOrder(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().getOrderId());
    }

    @Test
    void testCreateOrder_Negative() {
        OrderInventoryDTO dto = new OrderInventoryDTO();
        when(orderService.createOrder(dto)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> controller.createOrder(dto));
    }

    // GET /api/orders/{id}
    @Test
    void testGetOrder_Positive() {
        OrderInventoryDTO dto = buildOrderDTO(2);
        when(orderService.getOrderById(1)).thenReturn(dto);

        ResponseEntity<OrderInventoryDTO> response = controller.getOrder(1);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getOrderId());
    }

    @Test
    void testGetOrder_Negative() {
        when(orderService.getOrderById(999)).thenThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, () -> controller.getOrder(999));
    }

    // GET /api/orders
    @Test
    void testGetAllOrders_Positive() {
        List<OrderInventoryDTO> list = Arrays.asList(new OrderInventoryDTO(), new OrderInventoryDTO());
        when(orderService.getAllOrders()).thenReturn(list);

        ResponseEntity<List<OrderInventoryDTO>> response = controller.getAllOrders();
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetAllOrders_Negative() {
        when(orderService.getAllOrders()).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> controller.getAllOrders());
    }

    // PUT /api/orders/{id}
    @Test
    void testUpdateOrder_Positive() {
        OrderInventoryDTO dto = buildOrderDTO(3);
        when(orderService.updateOrder(eq(1), any())).thenReturn(dto);

        ResponseEntity<OrderInventoryDTO> response = controller.updateOrder(1, dto);
        assertEquals(3, response.getBody().getOrderId());
    }

    @Test
    void testUpdateOrder_Negative() {
        when(orderService.updateOrder(eq(1), any())).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> controller.updateOrder(1, new OrderInventoryDTO()));
    }

    // DELETE /api/orders/{id}
    @Test
    void testDeleteOrder_Positive() {
        doNothing().when(orderService).deleteOrder(1);
        ResponseEntity<Void> response = controller.deleteOrder(1);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void testDeleteOrder_Negative() {
        doThrow(RuntimeException.class).when(orderService).deleteOrder(1);
        assertThrows(RuntimeException.class, () -> controller.deleteOrder(1));
    }

    // POST /api/orders/reserve
    @Test
    void testReserveInventory_Positive() {
        List<ReserveRequest> requestList = List.of(new ReserveRequest());
        doNothing().when(inventoryService).reserveInventory(requestList);

        ResponseEntity<String> response = controller.reserveInventory(requestList);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Inventory reserved successfully", response.getBody());
    }

    @Test
    void testReserveInventory_Negative() {
        List<ReserveRequest> requestList = List.of(new ReserveRequest());
        doThrow(RuntimeException.class).when(inventoryService).reserveInventory(requestList);

        assertThrows(RuntimeException.class, () -> controller.reserveInventory(requestList));
    }

    // GET /api/orders/active
    @Test
    void testGetActiveOrders_Positive() {
        List<OrderInventoryDTO> list = List.of(new OrderInventoryDTO());
        when(orderService.getActiveOrders()).thenReturn(list);

        ResponseEntity<List<OrderInventoryDTO>> response = controller.getActiveOrders();
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetActiveOrders_Negative() {
        when(orderService.getActiveOrders()).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> controller.getActiveOrders());
    }

    // PUT /api/orders/allocate/{orderId}
    @Test
    void testAllocateOrCancelOrder_Allocate() {
        AllocateInventoryDTO dto = new AllocateInventoryDTO();
        dto.setIsCancelled(0);
        doNothing().when(orderService).allocateInventory(1, dto);

        ResponseEntity<String> response = controller.allocateOrCancelOrder(1, dto);
        assertEquals("Order allocated successfully.", response.getBody());
    }

    @Test
    void testAllocateOrCancelOrder_Cancel() {
        AllocateInventoryDTO dto = new AllocateInventoryDTO();
        dto.setIsCancelled(1);
        doNothing().when(orderService).cancelOrder(1, dto);

        ResponseEntity<String> response = controller.allocateOrCancelOrder(1, dto);
        assertEquals("Order cancelled and inventory updated.", response.getBody());
    }

    // PUT /api/orders/cancel/{orderId}
    @Test
    void testCancelOrder_ValidCancel() {
        AllocateInventoryDTO dto = new AllocateInventoryDTO();
        dto.setIsCancelled(1);
        doNothing().when(orderService).cancelOrder(1, dto);

        ResponseEntity<String> response = controller.cancelOrder(1, dto);
        assertEquals("Order cancelled and inventory updated.", response.getBody());
    }

    @Test
    void testCancelOrder_InvalidRequest() {
        AllocateInventoryDTO dto = new AllocateInventoryDTO();
        dto.setIsCancelled(0);

        ResponseEntity<String> response = controller.cancelOrder(1, dto);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid cancellation request.", response.getBody());
    }
}
