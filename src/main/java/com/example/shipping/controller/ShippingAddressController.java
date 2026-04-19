package com.example.shipping.controller;

import com.example.shipping.dto.ApiResponse;
import com.example.shipping.dto.ShippingAddressRequest;
import com.example.shipping.dto.ShippingAddressResponse;
import com.example.shipping.entity.ShippingAddress;
import com.example.shipping.service.ShippingAddressService;
import com.example.shipping.util.DtoMapper;
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
    public ResponseEntity<ApiResponse<List<ShippingAddressResponse>>> getAllShippingAddresses() {
        List<ShippingAddress> addresses = shippingAddressService.getAllShippingAddresses();
        List<ShippingAddressResponse> responses = DtoMapper.toShippingAddressResponseList(addresses);
        
        ApiResponse<List<ShippingAddressResponse>> response = new ApiResponse<>(
            true,
            "Shipping addresses retrieved successfully",
            responses,
            200
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShippingAddressResponse>> getShippingAddressById(@PathVariable Long id) {
        ShippingAddress address = shippingAddressService.getShippingAddressById(id);
        ShippingAddressResponse addressResponse = DtoMapper.toShippingAddressResponse(address);
        
        ApiResponse<ShippingAddressResponse> response = new ApiResponse<>(
            true,
            "Shipping address retrieved successfully",
            addressResponse,
            200
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ShippingAddressResponse>> createShippingAddress(@Valid @RequestBody ShippingAddressRequest request) {
        ShippingAddress address = shippingAddressService.createShippingAddress(request);
        ShippingAddressResponse addressResponse = DtoMapper.toShippingAddressResponse(address);
        
        ApiResponse<ShippingAddressResponse> response = new ApiResponse<>(
            true,
            "Shipping address created successfully",
            addressResponse,
            201
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ShippingAddressResponse>> updateShippingAddress(@PathVariable Long id, @Valid @RequestBody ShippingAddressRequest request) {
        ShippingAddress address = shippingAddressService.updateShippingAddress(id, request);
        ShippingAddressResponse addressResponse = DtoMapper.toShippingAddressResponse(address);
        
        ApiResponse<ShippingAddressResponse> response = new ApiResponse<>(
            true,
            "Shipping address updated successfully",
            addressResponse,
            200
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteShippingAddress(@PathVariable Long id) {
        shippingAddressService.deleteShippingAddress(id);
        
        ApiResponse<Void> response = new ApiResponse<>(
            true,
            "Shipping address deleted successfully",
            null,
            204
        );
        return ResponseEntity.noContent().build();
    }
}
