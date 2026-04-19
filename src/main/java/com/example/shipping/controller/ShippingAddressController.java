package com.example.shipping.controller;

import com.example.shipping.dto.ShippingAddressRequest;
import com.example.shipping.entity.ShippingAddress;
import com.example.shipping.service.ShippingAddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/shipping-addresses")
public class ShippingAddressController {

    @Autowired
    private ShippingAddressService shippingAddressService;

    @GetMapping
    public ResponseEntity<List<ShippingAddress>> getAllShippingAddresses() {
        return ResponseEntity.ok(shippingAddressService.getAllShippingAddresses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShippingAddress> getShippingAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(shippingAddressService.getShippingAddressById(id));
    }

    @PostMapping
    public ResponseEntity<ShippingAddress> createShippingAddress(@Valid @RequestBody ShippingAddressRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shippingAddressService.createShippingAddress(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShippingAddress> updateShippingAddress(@PathVariable Long id, @Valid @RequestBody ShippingAddressRequest request) {
        return ResponseEntity.ok(shippingAddressService.updateShippingAddress(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShippingAddress(@PathVariable Long id) {
        shippingAddressService.deleteShippingAddress(id);
        return ResponseEntity.noContent().build();
    }
}
