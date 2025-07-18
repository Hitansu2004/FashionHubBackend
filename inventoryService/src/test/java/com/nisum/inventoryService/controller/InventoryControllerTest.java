package com.nisum.inventoryService.controller;

import com.nisum.inventoryService.dao.Inventory;
import com.nisum.inventoryService.dto.*;
import com.nisum.inventoryService.service.AddInventoryService;
import com.nisum.inventoryService.service.AdjustInventoryService;
import com.nisum.inventoryService.service.InventoryService;
import com.nisum.inventoryService.service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryControllerTest {

    @Mock
    private InventoryService inventoryService;

    @Mock
    private AdjustInventoryService adjustInventoryService;

    @Mock
    private AddInventoryService addInventoryService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private InventoryController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddInventory() {
        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setSku("SKU123");
        inventory.setAvailableQty(100);

        when(inventoryService.addInventory(any())).thenReturn(inventory);

        ResponseEntity<Inventory> response = controller.addInventory(inventory);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(inventory, response.getBody());
        verify(inventoryService).addInventory(inventory);
    }

    @Test
    void testAddInventoryDtoSuccess() {
        InventoryDTO dto = new InventoryDTO();
        dto.setSku("SKU123");
        dto.setAvailableQty(50);

        // Prepare the returned DTO
        InventoryDTO returnedDto = new InventoryDTO(1L, "SKU123", 1, "LocationA", 50);

        // Mocking the service call to return the DTO
        when(addInventoryService.addInventory(any(InventoryDTO.class))).thenReturn(returnedDto);

        ResponseEntity<String> response = controller.addInventoryDto(dto);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Inventory added successfully"));
        verify(addInventoryService).addInventory(dto);
    }





    @Test
    void testAddInventoryDtoBadRequest() {
        InventoryDTO dto = new InventoryDTO();

        doThrow(new IllegalArgumentException("Invalid input"))
                .when(addInventoryService).addInventory(dto);

        ResponseEntity<String> response = controller.addInventoryDto(dto);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Invalid input"));
    }

    @Test
    void testGetAllInventory() {
        Inventory inventory1 = new Inventory();
        inventory1.setId(1L);
        Inventory inventory2 = new Inventory();
        inventory2.setId(2L);

        when(inventoryService.getAllInventory()).thenReturn(Arrays.asList(inventory1, inventory2));

        ResponseEntity<List<Inventory>> response = controller.getAllInventory();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testUpdateInventory() {
        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setSku("SKU123");
        inventory.setAvailableQty(200);

        when(inventoryService.updateInventory(eq(1), any())).thenReturn(inventory);

        ResponseEntity<Inventory> response = controller.updateInventory(1, inventory);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(inventory, response.getBody());
    }

    @Test
    void testDeleteInventory() {
        doNothing().when(inventoryService).deleteInventory(1);

        ResponseEntity<Void> response = controller.deleteInventory(1);

        assertEquals(204, response.getStatusCodeValue());
        verify(inventoryService).deleteInventory(1);
    }

}
