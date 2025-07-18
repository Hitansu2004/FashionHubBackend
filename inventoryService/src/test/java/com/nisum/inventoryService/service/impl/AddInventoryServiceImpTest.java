package com.nisum.inventoryService.service.impl;

import com.nisum.inventoryService.dao.Inventory;
import com.nisum.inventoryService.dto.InventoryDTO;
import com.nisum.inventoryService.repository.AddInventoryRepo;
import com.nisum.inventoryService.utils.MapperUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddInventoryServiceImpTest {

    @Mock
    private AddInventoryRepo addInventoryRepo;

    @Mock
    private MapperUtil mapperUtil;

    @InjectMocks
    private AddInventoryServiceImp service;

    @Test
    void testAddInventoryThrowsIfSkuExists() {
        InventoryDTO dto = new InventoryDTO();
        dto.setSku("sku123");

        when(addInventoryRepo.existsBySku("sku123")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.addInventory(dto));

        assertEquals("SKU already exists", exception.getMessage());
        verify(addInventoryRepo, times(1)).existsBySku("sku123");
    }

    @Test
    void testAddInventorySuccess() {
        InventoryDTO dto = new InventoryDTO();
        dto.setSku("sku456");

        Inventory inventory = new Inventory();
        inventory.setSku("sku456");

        when(addInventoryRepo.existsBySku("sku456")).thenReturn(false);
        when(mapperUtil.mapToInventory(dto)).thenReturn(inventory);
        when(addInventoryRepo.save(inventory)).thenReturn(inventory);
        when(mapperUtil.mapToInventoryDto(inventory)).thenReturn(dto);

        InventoryDTO result = service.addInventory(dto);

        assertNotNull(result);
        assertEquals("sku456", result.getSku());
    }

    @Test
    void testGetAllInventory() {
        Inventory inventory = new Inventory();
        inventory.setSku("sku-test");

        InventoryDTO dto = new InventoryDTO();
        dto.setSku("sku-test");

        when(addInventoryRepo.findAll()).thenReturn(List.of(inventory));
        when(mapperUtil.mapToInventoryDto(inventory)).thenReturn(dto);

        List<InventoryDTO> result = service.getAllInventory();

        assertEquals(1, result.size());
        assertEquals("sku-test", result.get(0).getSku());
    }

    @Test
    void testGetAvailabilityForSkus() {
        Inventory inv1 = new Inventory("sku1", "loc", 10, 1);
        Inventory inv2 = new Inventory("sku2", "loc", 5, 2);

        List<Inventory> allInventory = List.of(inv1, inv2);

        when(addInventoryRepo.findAll()).thenReturn(allInventory);

        List<String> skuList = List.of("sku1", "sku2");

        List<Map<String, Object>> result = service.getAvailabilityForSkus(skuList);

        assertEquals(2, result.size());
        assertEquals("sku1", result.get(0).get("sku"));
        assertEquals(10, result.get(0).get("availableQty"));
    }
}
