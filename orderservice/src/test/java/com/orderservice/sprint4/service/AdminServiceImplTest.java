package com.orderservice.sprint4.service;

import com.orderservice.sprint4.dto.OrderItemStatusRequestDTO;
import com.orderservice.sprint4.dto.OrderStageDTO;
import com.orderservice.sprint4.dto.ShipmentItemListDTO;
import com.orderservice.sprint4.exception.OrderNotFoundException;
import com.orderservice.sprint4.model.Order;
import com.orderservice.sprint4.model.OrderItem;
import com.orderservice.sprint4.model.ShipmentItem;
import com.orderservice.sprint4.model.enmus.ShipmentItemStatus;
import com.orderservice.sprint4.repository.ShipmentItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    private AdminServiceImpl adminService;
    private ShipmentItemRepository shipmentItemRepository;
    private RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        shipmentItemRepository = mock(ShipmentItemRepository.class);
        restTemplate = mock(RestTemplate.class);

        adminService = new AdminServiceImpl();

        ReflectionTestUtils.setField(adminService, "shipmentItemRepository", shipmentItemRepository);
        ReflectionTestUtils.setField(adminService, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(adminService, "INVENTORY_SERVICE_INVENTORY_ALLOCATE_URL", "http://dummy-service/inventory/allocate/");
        ReflectionTestUtils.setField(adminService, "INVENTORY_SERVICE_INVENTORY_CANCEL_URL", "http://dummy-service/inventory/cancel/");
    }

    private ShipmentItem createShipmentItem(int orderItemId, ShipmentItemStatus status) {
        Order order = new Order();
        order.setOrderId(500);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(orderItemId);
        orderItem.setOrder(order);

        ShipmentItem shipmentItem = new ShipmentItem();
        shipmentItem.setItemStatus(status);
        shipmentItem.setOrderItem(orderItem);
        shipmentItem.setShipmentDate(LocalDateTime.now());

        return shipmentItem;
    }

    @Test
    void testOrderShipped_Success() {
        OrderItemStatusRequestDTO dto = new OrderItemStatusRequestDTO();
        dto.setOrderItemId(1001);
        dto.setItemStatus(ShipmentItemStatus.InTransit);

        ShipmentItem item = createShipmentItem(1001, ShipmentItemStatus.Pending);
        when(shipmentItemRepository.findByOrderItemOrderItemId(1001)).thenReturn(item);

        adminService.orderShipped(dto);

        verify(shipmentItemRepository).save(item);
        verify(restTemplate).put(eq("http://dummy-service/inventory/allocate/1001"), any(OrderStageDTO.class));
    }

    @Test
    void testOrderShipped_InvalidStatus_ThrowsException() {
        OrderItemStatusRequestDTO dto = new OrderItemStatusRequestDTO();
        dto.setOrderItemId(1001);
        dto.setItemStatus(ShipmentItemStatus.Delivered); // Invalid for shipped

        assertThrows(RuntimeException.class, () -> adminService.orderShipped(dto));
    }

    @Test
    void testOrderShipped_ItemAlreadyUpdated_ThrowsException() {
        OrderItemStatusRequestDTO dto = new OrderItemStatusRequestDTO();
        dto.setOrderItemId(1001);
        dto.setItemStatus(ShipmentItemStatus.InTransit);

        ShipmentItem item = createShipmentItem(1001, ShipmentItemStatus.InTransit);
        when(shipmentItemRepository.findByOrderItemOrderItemId(1001)).thenReturn(item);

        assertThrows(RuntimeException.class, () -> adminService.orderShipped(dto));
    }

    @Test
    void testOrderDelivered_Success() {
        OrderItemStatusRequestDTO dto = new OrderItemStatusRequestDTO();
        dto.setOrderItemId(2002);
        dto.setItemStatus(ShipmentItemStatus.Delivered);

        ShipmentItem item = createShipmentItem(2002, ShipmentItemStatus.InTransit);
        when(shipmentItemRepository.findByOrderItemOrderItemId(2002)).thenReturn(item);

        adminService.orderDelivered(dto);

        verify(shipmentItemRepository).save(item);
    }

    @Test
    void testOrderDelivered_InvalidStatus_ThrowsException() {
        OrderItemStatusRequestDTO dto = new OrderItemStatusRequestDTO();
        dto.setOrderItemId(2002);
        dto.setItemStatus(ShipmentItemStatus.Pending); // Invalid

        assertThrows(RuntimeException.class, () -> adminService.orderDelivered(dto));
    }

    @Test
    void testOrderCancelled_Success() {
        OrderItemStatusRequestDTO dto = new OrderItemStatusRequestDTO();
        dto.setOrderItemId(3003);
        dto.setItemStatus(ShipmentItemStatus.Cancelled);

        ShipmentItem item = createShipmentItem(3003, ShipmentItemStatus.Pending);
        when(shipmentItemRepository.findByOrderItemOrderItemId(3003)).thenReturn(item);

        adminService.orderCanelled(dto);

        verify(shipmentItemRepository).save(item);
        verify(restTemplate).put(eq("http://dummy-service/inventory/cancel/3003"), any(OrderStageDTO.class));
    }

    @Test
    void testOrderCancelled_InvalidStatus_ThrowsException() {
        OrderItemStatusRequestDTO dto = new OrderItemStatusRequestDTO();
        dto.setOrderItemId(3003);
        dto.setItemStatus(ShipmentItemStatus.Delivered); // Cannot cancel delivered item

        assertThrows(RuntimeException.class, () -> adminService.orderCanelled(dto));
    }

    @Test
    void testGetShipmentByStatus_Success() {
        ShipmentItem item = createShipmentItem(4004, ShipmentItemStatus.Delivered);
        when(shipmentItemRepository.findByItemStatus(ShipmentItemStatus.Delivered)).thenReturn(List.of(item));

        List<ShipmentItemListDTO> result = adminService.getShipmentByStatus(ShipmentItemStatus.Delivered);

        assertEquals(1, result.size());
        assertEquals(4004, result.get(0).getOrderItemId());
    }

    @Test
    void testGetShipmentByStatus_EmptyList_ThrowsException() {
        when(shipmentItemRepository.findByItemStatus(ShipmentItemStatus.InTransit)).thenReturn(List.of());

        assertThrows(RuntimeException.class, () -> adminService.getShipmentByStatus(ShipmentItemStatus.InTransit));
    }

    @Test
    void testOrderShipped_OrderItemNotFound_ThrowsException() {
        OrderItemStatusRequestDTO dto = new OrderItemStatusRequestDTO();
        dto.setOrderItemId(9999);
        dto.setItemStatus(ShipmentItemStatus.InTransit);

        when(shipmentItemRepository.findByOrderItemOrderItemId(9999)).thenReturn(null);

        assertThrows(OrderNotFoundException.class, () -> adminService.orderShipped(dto));
    }
}
