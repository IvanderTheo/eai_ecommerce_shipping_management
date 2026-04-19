package com.example.shipping.controller;

import com.example.shipping.dto.ApiResponse;
import com.example.shipping.dto.CarrierRequest;
import com.example.shipping.dto.CarrierResponse;
import com.example.shipping.entity.Carrier;
import com.example.shipping.service.CarrierService;
import com.example.shipping.util.DtoMapper;
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
    public ResponseEntity<ApiResponse<List<CarrierResponse>>> getAllCarriers() {
        List<Carrier> carriers = carrierService.getAllCarriers();
        List<CarrierResponse> responses = DtoMapper.toCarrierResponseList(carriers);
        
        ApiResponse<List<CarrierResponse>> response = new ApiResponse<>(
            true,
            "Carriers retrieved successfully",
            responses,
            200
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CarrierResponse>> getCarrierById(@PathVariable Long id) {
        Carrier carrier = carrierService.getCarrierById(id);
        CarrierResponse carrierResponse = DtoMapper.toCarrierResponse(carrier);
        
        ApiResponse<CarrierResponse> response = new ApiResponse<>(
            true,
            "Carrier retrieved successfully",
            carrierResponse,
            200
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CarrierResponse>> createCarrier(@Valid @RequestBody CarrierRequest request) {
        Carrier carrier = carrierService.createCarrier(request);
        CarrierResponse carrierResponse = DtoMapper.toCarrierResponse(carrier);
        
        ApiResponse<CarrierResponse> response = new ApiResponse<>(
            true,
            "Carrier created successfully",
            carrierResponse,
            201
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CarrierResponse>> updateCarrier(@PathVariable Long id, @Valid @RequestBody CarrierRequest request) {
        Carrier carrier = carrierService.updateCarrier(id, request);
        CarrierResponse carrierResponse = DtoMapper.toCarrierResponse(carrier);
        
        ApiResponse<CarrierResponse> response = new ApiResponse<>(
            true,
            "Carrier updated successfully",
            carrierResponse,
            200
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCarrier(@PathVariable Long id) {
        carrierService.deleteCarrier(id);
        
        ApiResponse<Void> response = new ApiResponse<>(
            true,
            "Carrier deleted successfully",
            null,
            204
        );
        return ResponseEntity.noContent().build();
    }
}
