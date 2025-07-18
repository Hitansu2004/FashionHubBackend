package com.nisum.inventoryService.service.impl;

import com.nisum.inventoryService.dao.Inventory;
import com.nisum.inventoryService.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AdjustInventoryServiceImplTest {

    @Mock
    private InventoryRepository repository;

    @InjectMocks
    private AdjustInventoryServiceImpl service;

    @Test
    void testAdjustInventoryReturnsNullIfNotFound() {
        when(repository.findBySku("sku")).thenReturn(Optional.empty());
        assertNull(service.adjustInventory("sku", 5));
    }

    @Test
    void testAdjustInventoryUpdatesQty() {
        Inventory inv = new Inventory("sku", "loc", 10, 1);
        when(repository.findBySku("sku")).thenReturn(Optional.of(inv));
        when(repository.save(any())).thenReturn(inv);

        Inventory result = service.adjustInventory("sku", 5);

        assertNotNull(result);
        assertEquals(15, result.getAvailableQty());
        verify(repository).save(inv);
    }

    @Test
    void testCancelInventoryReturnsNullIfNotFound() {
        when(repository.findBySku("sku")).thenReturn(Optional.empty());
        assertNull(service.cancelInventory("sku", 3));
    }

    @Test
    void testCancelInventoryDecreasesQtySafely() {
        Inventory inv = new Inventory("sku", "loc", 5, 1);
        when(repository.findBySku("sku")).thenReturn(Optional.of(inv));
        when(repository.save(any())).thenReturn(inv);

        Inventory result = service.cancelInventory("sku", 3);

        assertNotNull(result);
        assertEquals(2, result.getAvailableQty());
        verify(repository).save(inv);
    }

    @Test
    void testCancelInventoryDoesNotGoBelowZero() {
        Inventory inv = new Inventory("sku", "loc", 2, 1);
        when(repository.findBySku("sku")).thenReturn(Optional.of(inv));
        when(repository.save(any())).thenReturn(inv);

        Inventory result = service.cancelInventory("sku", 5);

        assertEquals(0, result.getAvailableQty());
    }

    @Test
    void testGetAllReturnsList() {
        Inventory inv1 = new Inventory("sku1", "loc1", 5, 1);
        Inventory inv2 = new Inventory("sku2", "loc2", 10, 2);

        List<Inventory> mockList = Arrays.asList(inv1, inv2);

        when(repository.findAll()).thenReturn(mockList);

        List<Inventory> result = service.getall();

        assertEquals(2, result.size());
        assertEquals("sku1", result.get(0).getSku());
        assertEquals("sku2", result.get(1).getSku());
    }
}
