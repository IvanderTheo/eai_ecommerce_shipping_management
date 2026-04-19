package com.example.shipping.controller;

import com.example.shipping.dto.*;
import com.example.shipping.entity.Shipment;
import com.example.shipping.service.ShipmentService;
import com.example.shipping.util.DtoMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllShipments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Shipment> shipmentPage = shipmentService.getAllShipmentsPaginated(pageable);
        
        Map<String, Object> data = new HashMap<>();
        data.put("content", DtoMapper.toShipmentResponseList(shipmentPage.getContent()));
        data.put("totalElements", shipmentPage.getTotalElements());
        data.put("totalPages", shipmentPage.getTotalPages());
        data.put("currentPage", shipmentPage.getNumber());
        data.put("pageSize", shipmentPage.getSize());
        
        ApiResponse<Map<String, Object>> response = new ApiResponse<>(
            true,
            "Shipments retrieved successfully",
            data,
            200
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShipmentResponse>> getShipmentById(@PathVariable Long id) {
        Shipment shipment = shipmentService.getShipmentById(id);
        ShipmentResponse shipmentResponse = DtoMapper.toShipmentResponse(shipment);
        
        ApiResponse<ShipmentResponse> response = new ApiResponse<>(
            true,
            "Shipment retrieved successfully",
            shipmentResponse,
            200
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/track/{trackingNumber}")
    public ResponseEntity<ApiResponse<ShipmentResponse>> getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
        Shipment shipment = shipmentService.getShipmentByTrackingNumber(trackingNumber);
        ShipmentResponse shipmentResponse = DtoMapper.toShipmentResponse(shipment);
        
        ApiResponse<ShipmentResponse> response = new ApiResponse<>(
            true,
            "Shipment tracked successfully",
            shipmentResponse,
            200
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ShipmentResponse>> createShipment(@Valid @RequestBody ShipmentRequest request) {
        Shipment shipment = shipmentService.createShipment(request);
        ShipmentResponse shipmentResponse = DtoMapper.toShipmentResponse(shipment);
        
        ApiResponse<ShipmentResponse> response = new ApiResponse<>(
            true,
            "Shipment created successfully",
            shipmentResponse,
            201
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ShipmentResponse>> updateShipmentStatus(@PathVariable Long id, @RequestParam String status) {
        Shipment shipment = shipmentService.updateShipmentStatus(id, status);
        ShipmentResponse shipmentResponse = DtoMapper.toShipmentResponse(shipment);
        
        ApiResponse<ShipmentResponse> response = new ApiResponse<>(
            true,
            "Shipment status updated successfully",
            shipmentResponse,
            200
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteShipment(@PathVariable Long id) {
        shipmentService.deleteShipment(id);
        
        ApiResponse<Void> response = new ApiResponse<>(
            true,
            "Shipment deleted successfully",
            null,
            204
        );
        return ResponseEntity.noContent().build();
    }
}
