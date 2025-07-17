package com.catalogservice.controller;

import com.catalogservice.dto.PromoRequestDto;
import com.catalogservice.dto.PromoResponseDto;
import com.catalogservice.service.PromoService;
import jakarta.validation.constraints.Digits;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

<<<<<<< HEAD
@CrossOrigin(origins = "http://localhost:3000")
=======
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
@RestController
@RequestMapping("/api/promos")
public class PromoController {
    private final PromoService service;

    @Autowired
    public PromoController(PromoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PromoResponseDto> save(@RequestBody PromoRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<PromoResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<PromoResponseDto> update(@PathVariable Long id, @RequestBody PromoRequestDto dto) {
//        return ResponseEntity.ok(service.update(id, dto));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        service.delete(id);
//        return ResponseEntity.noContent().build();
//    }

    @PutMapping("/code/{promoCode}")
    public ResponseEntity<PromoResponseDto> updateByPromoCode(@PathVariable String promoCode, @RequestBody PromoRequestDto dto) {
        return ResponseEntity.ok(service.updateByPromoCode(promoCode, dto));
    }

    @DeleteMapping("/code/{promoCode}")
    public ResponseEntity<Void> deleteByPromoCode(@PathVariable String promoCode) {
        service.deleteByPromoCode(promoCode);
        return ResponseEntity.noContent().build();
    }

<<<<<<< HEAD
    @GetMapping("/by-product-ids")
=======
    @PostMapping("/by-product-ids")
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
    public ResponseEntity<List<PromoResponseDto>> getPromosByProductIds(@RequestBody List<Integer> productIds) {
        List<PromoResponseDto> responseList = service.getPromosByProductIds(productIds);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<PromoResponseDto>> getPromosByProductId(@PathVariable("productId") Integer productId) {
        List<PromoResponseDto> promos = service.getPromosByProductIds(List.of(productId));
        // Ensure discount is always present (null) for each promo
        promos.forEach(promo -> promo.setDiscount(null));
        return ResponseEntity.ok(promos);
    }
<<<<<<< HEAD

    @GetMapping("/amount-by-code")
    public ResponseEntity<?> getAmountByPromoCode(@RequestParam String promocode) {
        Integer amount = service.getAmountByPromoCode(promocode);
        if (amount != null) {
            return ResponseEntity.ok(java.util.Map.of("amount", amount));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(java.util.Map.of("message", "Promocode not found"));
        }
    }
=======
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
}
