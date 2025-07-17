package com.catalogservice.controller;

import com.catalogservice.dto.PromoProductJoinRequestDto;
import com.catalogservice.dto.PromoProductJoinResponseDto;
<<<<<<< HEAD
import com.catalogservice.model.PromoProductJoinId;
=======
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
import com.catalogservice.service.PromoProductJoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

<<<<<<< HEAD
@CrossOrigin(origins = "http://localhost:3000")
=======
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
@RestController
@RequestMapping("/api/promo-product-joins")
public class PromoProductJoinController {
    private final PromoProductJoinService service;

    @Autowired
    public PromoProductJoinController(PromoProductJoinService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PromoProductJoinResponseDto> save(@RequestBody PromoProductJoinRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<PromoProductJoinResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

<<<<<<< HEAD
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable Integer productId) {
        service.deleteByProductId(productId);
=======
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
>>>>>>> dbc3b3ff9fe3913d85dd004494b32a674116784b
        return ResponseEntity.noContent().build();
    }
}
