package com.nisum.inventoryService.controller;

import com.nisum.inventoryService.dto.AllocateInventoryDTO;
import com.nisum.inventoryService.dto.OrderInventoryDTO;
import com.nisum.inventoryService.dto.ReserveRequest;
import com.nisum.inventoryService.service.InventoryService;
import com.nisum.inventoryService.service.OrderInventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderInventoryController {

    @Autowired
    private OrderInventoryService service;

    @Autowired
    private InventoryService inventoryService;

    // Create a new order
    @PostMapping
    public ResponseEntity<OrderInventoryDTO> createOrder(@RequestBody OrderInventoryDTO dto) {
        return ResponseEntity.ok(service.createOrder(dto));
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderInventoryDTO> getOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    // Get all orders
    @GetMapping
    public ResponseEntity<List<OrderInventoryDTO>> getAllOrders() {
        return ResponseEntity.ok(service.getAllOrders());
    }

    // Update order by ID
    @PutMapping("/{id}")
    public ResponseEntity<OrderInventoryDTO> updateOrder(@PathVariable Integer id, @RequestBody OrderInventoryDTO dto) {
        return ResponseEntity.ok(service.updateOrder(id, dto));
    }

    // Delete order by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    // Reserve inventory for an order
    @PostMapping("/reserve")
    public ResponseEntity<String> reserveInventory(@RequestBody List<ReserveRequest> request) {
        inventoryService.reserveInventory(request);
        return ResponseEntity.ok("Inventory reserved successfully");
    }

    // Get all active (non-zero reservedQty) orders
    @GetMapping("/active")
    public ResponseEntity<List<OrderInventoryDTO>> getActiveOrders() {
        return ResponseEntity.ok(service.getActiveOrders());
    }

    // Allocate or cancel inventory based on isCancelled flag
    @PutMapping("/allocate/{orderId}")
    public ResponseEntity<String> allocateOrCancelOrder(
            @PathVariable Integer orderId,
            @RequestBody AllocateInventoryDTO dto) {

        if (dto.getIsCancelled() != null && dto.getIsCancelled() == 1) {
            service.cancelOrder(orderId, dto); // ✅ fixed: pass dto
            return ResponseEntity.ok("Order cancelled and inventory updated.");
        } else {
            service.allocateInventory(orderId, dto); // ✅ fixed: pass dto
            return ResponseEntity.ok("Order allocated successfully.");
        }
    }

    // (Optional) Separate endpoint to cancel an order
    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(
            @PathVariable Integer orderId,
            @RequestBody AllocateInventoryDTO dto) {

        if (dto.getIsCancelled() != null && dto.getIsCancelled() == 1) {
            service.cancelOrder(orderId, dto); // ✅ fixed: pass dto
            return ResponseEntity.ok("Order cancelled and inventory updated.");
        } else {
            return ResponseEntity.badRequest().body("Invalid cancellation request.");
        }
    }
}
