package com.nisum.inventoryService.service.impl;

import com.nisum.inventoryService.dao.OrderInventory;
import com.nisum.inventoryService.dto.OrderInventoryDTO;
import com.nisum.inventoryService.exception.ResourceNotFoundException;
import com.nisum.inventoryService.repository.InventoryRepository;
import com.nisum.inventoryService.repository.OrderInventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderInventoryServiceImplTest {

    @Mock
    private OrderInventoryRepository repository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private OrderInventoryServiceImpl service;

    @Test
    void testCreateOrder() {
        OrderInventory order = new OrderInventory();
        when(repository.save(any())).thenReturn(order);
        OrderInventoryDTO result = service.createOrder(new OrderInventoryDTO());
        assertNotNull(result);
    }

    @Test
    void testGetOrderByIdSuccess() {
        OrderInventory order = new OrderInventory();
        order.setOrderId(1);
        when(repository.findById(1)).thenReturn(Optional.of(order));
        OrderInventoryDTO result = service.getOrderById(1);
        assertEquals(1, result.getOrderId());
    }

    @Test
    void testGetOrderByIdThrowsIfNotFound() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getOrderById(1));
    }

    @Test
    void testGetAllOrders() {
        when(repository.findAll()).thenReturn(List.of(new OrderInventory()));
        List<OrderInventoryDTO> result = service.getAllOrders();
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateOrderSuccess() {
        OrderInventory existing = new OrderInventory();
        existing.setOrderId(1);
        OrderInventoryDTO dto = new OrderInventoryDTO();
        dto.setOrderId(1);
        dto.setSku("sku");
        dto.setReservedQty(5);
        dto.setCategoryId(2);

        when(repository.findById(1)).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenReturn(existing);

        OrderInventoryDTO result = service.updateOrder(1, dto);
        assertEquals(1, result.getOrderId());
        assertEquals("sku", result.getSku());
    }

    @Test
    void testUpdateOrderNotFound() {
        OrderInventoryDTO dto = new OrderInventoryDTO();
        when(repository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.updateOrder(1, dto));
    }

}