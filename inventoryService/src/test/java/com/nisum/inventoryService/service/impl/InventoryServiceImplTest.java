package com.nisum.inventoryService.service.impl;

import com.nisum.inventoryService.dao.Inventory;
import com.nisum.inventoryService.dao.OrderInventory;
import com.nisum.inventoryService.dto.ActiveInventoryDTO;
import com.nisum.inventoryService.dto.ReserveRequest;
import com.nisum.inventoryService.exception.ResourceNotFoundException;
import com.nisum.inventoryService.repository.InventoryRepository;
import com.nisum.inventoryService.repository.OrderInventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository repository;

    @Mock
    private OrderInventoryRepository orderInventoryRepository;

    @InjectMocks
    private InventoryServiceImpl service;

    @Test
    void testAddInventory() {
        Inventory inv = new Inventory("sku", "loc", 10, 1);
        when(repository.save(inv)).thenReturn(inv);
        Inventory result = service.addInventory(inv);
        assertEquals(inv, result);
    }

    @Test
    void testGetAllInventory() {
        List<Inventory> list = List.of(new Inventory("sku1", "loc", 5, 1));
        when(repository.findAll()).thenReturn(list);
        List<Inventory> result = service.getAllInventory();
        assertEquals(1, result.size());
        assertEquals("sku1", result.get(0).getSku());
    }

    @Test
    void testUpdateInventory() {
        Inventory inv = new Inventory("sku1", "loc1", 10, 1);
        inv.setId(1L);
        Inventory updated = new Inventory("sku2", "loc2", 15, 2);

        when(repository.findById(1)).thenReturn(Optional.of(inv));
        when(repository.save(any())).thenReturn(updated);

        Inventory result = service.updateInventory(1, updated);

        assertEquals("sku2", result.getSku());
        assertEquals("loc2", result.getLocation());
        assertEquals(15, result.getAvailableQty());
        assertEquals(2, result.getCategoryId());
    }

    @Test
    void testDeleteInventory() {
        doNothing().when(repository).deleteById(1);
        service.deleteInventory(1);
        verify(repository).deleteById(1);
    }

    @Test
    void testReserveInventoryThrowsIfOrderExists() {
        ReserveRequest req = new ReserveRequest(1, "sku", 3);
        when(orderInventoryRepository.existsByOrderId(1)).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> service.reserveInventory(List.of(req)));
    }

    @Test
    void testReserveInventoryThrowsIfInventoryNotFound() {
        ReserveRequest req = new ReserveRequest(1, "sku", 3);
        when(orderInventoryRepository.existsByOrderId(1)).thenReturn(false);
        when(repository.findBySku("sku")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.reserveInventory(List.of(req)));
    }

    @Test
    void testReserveInventoryThrowsIfInsufficientStock() {
        ReserveRequest req = new ReserveRequest(1, "sku", 10);
        Inventory inv = new Inventory("sku", "loc", 5, 1);

        when(orderInventoryRepository.existsByOrderId(1)).thenReturn(false);
        when(repository.findBySku("sku")).thenReturn(Optional.of(inv));

        assertThrows(IllegalArgumentException.class, () -> service.reserveInventory(List.of(req)));
    }

    @Test
    void testReserveInventorySuccess() {
        ReserveRequest req = new ReserveRequest(1, "sku", 5);
        Inventory inv = new Inventory("sku", "loc", 10, 1);

        when(orderInventoryRepository.existsByOrderId(1)).thenReturn(false);
        when(repository.findBySku("sku")).thenReturn(Optional.of(inv));
        when(repository.save(any())).thenReturn(inv);
        when(orderInventoryRepository.save(any())).thenReturn(new OrderInventory());

        assertDoesNotThrow(() -> service.reserveInventory(List.of(req)));
    }

    @Test
    void testGetActiveInventory() {
        Inventory inv1 = new Inventory("sku1", "loc", 5, 1);
        Inventory inv2 = new Inventory("sku2", "loc", 8, 2);
        when(repository.findByAvailableQtyGreaterThan(0)).thenReturn(List.of(inv1, inv2));

        List<ActiveInventoryDTO> result = service.getActiveInventory();
        assertEquals(2, result.size());
        assertEquals("sku1", result.get(0).getSku());
        assertEquals(5, result.get(0).getAvailableQty());
    }

    @Test
    void testGetAvailableQuantityReturnsStockValue() {
        Inventory inv = new Inventory("sku", "loc", 7, 1);
        when(repository.findBySku("sku")).thenReturn(Optional.of(inv));
        String result = service.getAvailableQuantity("sku");
        assertEquals("7", result);
    }

    @Test
    void testGetAvailableQuantityReturnsOutOfStock() {
        Inventory inv = new Inventory("sku", "loc", 0, 1);
        when(repository.findBySku("sku")).thenReturn(Optional.of(inv));
        String result = service.getAvailableQuantity("sku");
        assertEquals("out of stock", result);
    }

    @Test
    void testGetAvailableQuantitySkuDoesNotExist() {
        when(repository.findBySku("sku")).thenReturn(Optional.empty());
        String result = service.getAvailableQuantity("sku");
        assertEquals("sku does not exist", result);
    }
}
