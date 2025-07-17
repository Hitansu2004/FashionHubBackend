package com.nisum.productmanagement.controller;

import com.nisum.productmanagement.dto.SellerDto;
import com.nisum.productmanagement.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
@CrossOrigin(origins = "*")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<SellerDto>> getAllSellers() {
        List<SellerDto> sellers = sellerService.getAllSellers();
        return ResponseEntity.ok(sellers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerDto> getSellerById(@PathVariable Long id) {
        SellerDto seller = sellerService.getSellerById(id);
        return ResponseEntity.ok(seller);
    }

    @PostMapping
    public ResponseEntity<SellerDto> createSeller(@RequestBody SellerDto sellerDto) {
        SellerDto createdSeller = sellerService.createSeller(sellerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSeller);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerDto> updateSeller(@PathVariable Long id, @RequestBody SellerDto sellerDto) {
        SellerDto updatedSeller = sellerService.updateSeller(id, sellerDto);
        return ResponseEntity.ok(updatedSeller);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getSellerCount() {
        long count = sellerService.countSellers();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SellerDto>> searchSellers(@RequestParam String query) {
        List<SellerDto> sellers = sellerService.searchSellers(query);
        return ResponseEntity.ok(sellers);
    }

    @GetMapping("/by-name")
    public ResponseEntity<List<SellerDto>> getSellersByName(@RequestParam String name) {
        List<SellerDto> sellers = sellerService.getSellersByName(name);
        return ResponseEntity.ok(sellers);
    }

    @GetMapping("/by-contact")
    public ResponseEntity<List<SellerDto>> getSellersByContactName(@RequestParam String contactName) {
        List<SellerDto> sellers = sellerService.getSellersByContactName(contactName);
        return ResponseEntity.ok(sellers);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<SellerDto>> getSellersByCity(@PathVariable String city) {
        List<SellerDto> sellers = sellerService.getSellersByCity(city);
        return ResponseEntity.ok(sellers);
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<SellerDto>> getSellersByState(@PathVariable String state) {
        List<SellerDto> sellers = sellerService.getSellersByState(state);
        return ResponseEntity.ok(sellers);
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<SellerDto>> getSellersByCountry(@PathVariable String country) {
        List<SellerDto> sellers = sellerService.getSellersByCountry(country);
        return ResponseEntity.ok(sellers);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<SellerDto> getSellerByEmail(@PathVariable String email) {
        SellerDto seller = sellerService.getSellerByEmail(email);
        return ResponseEntity.ok(seller);
    }

    @GetMapping("/email/available")
    public ResponseEntity<Boolean> isEmailAvailable(@RequestParam String email) {
        boolean isAvailable = sellerService.isEmailAvailable(email);
        return ResponseEntity.ok(isAvailable);
    }
}
