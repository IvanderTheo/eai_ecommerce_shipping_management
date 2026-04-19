package com.example.shipping.controller;

import com.example.shipping.dto.CarrierRequest;
import com.example.shipping.entity.Carrier;
import com.example.shipping.service.CarrierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/carriers")
public class CarrierController {

    @Autowired
    private CarrierService carrierService;

    @GetMapping
    public ResponseEntity<List<Carrier>> getAllCarriers() {
        return ResponseEntity.ok(carrierService.getAllCarriers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrier> getCarrierById(@PathVariable Long id) {
        return ResponseEntity.ok(carrierService.getCarrierById(id));
    }

    @PostMapping
    public ResponseEntity<Carrier> createCarrier(@Valid @RequestBody CarrierRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carrierService.createCarrier(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrier> updateCarrier(@PathVariable Long id, @Valid @RequestBody CarrierRequest request) {
        return ResponseEntity.ok(carrierService.updateCarrier(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrier(@PathVariable Long id) {
        carrierService.deleteCarrier(id);
        return ResponseEntity.noContent().build();
    }
}
