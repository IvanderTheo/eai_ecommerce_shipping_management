package com.example.shipping.service;

import com.example.shipping.dto.ShipmentRequest;
import com.example.shipping.dto.PackageRequest;
import com.example.shipping.entity.*;
import com.example.shipping.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private CarrierRepository carrierRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private ParcelRepository parcelRepository;

    @Autowired
    private TrackingHistoryRepository trackingHistoryRepository;

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public Shipment getShipmentById(Long id) {
        return shipmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));
    }

    public Shipment getShipmentByTrackingNumber(String trackingNumber) {
        return shipmentRepository.findByTrackingNumber(trackingNumber)
            .orElseThrow(() -> new RuntimeException("Shipment not found with tracking number: " + trackingNumber));
    }

    @Transactional
    public Shipment createShipment(ShipmentRequest request) {
        Carrier carrier = carrierRepository.findById(request.getCarrierId())
            .orElseThrow(() -> new RuntimeException("Carrier not found with id: " + request.getCarrierId()));

        ShippingAddress address = shippingAddressRepository.findById(request.getShippingAddressId())
            .orElseThrow(() -> new RuntimeException("Shipping address not found with id: " + request.getShippingAddressId()));

        String trackingNumber = "SHIP" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
        
        Double totalWeight = 0.0;
        for (PackageRequest parcelReq : request.getParcels()) {
            totalWeight += parcelReq.getWeight();
        }

        Double shippingCost = totalWeight * carrier.getCostPerKg();

        Shipment shipment = new Shipment(trackingNumber, carrier, address, totalWeight, shippingCost);
        shipment.setEstimatedDelivery(LocalDateTime.now().plusDays(carrier.getEstimatedDays()));
        Shipment savedShipment = shipmentRepository.save(shipment);

        for (PackageRequest parcelReq : request.getParcels()) {
            Parcel parcel = new Parcel(
                parcelReq.getDescription(),
                parcelReq.getWeight(),
                parcelReq.getWidth(),
                parcelReq.getHeight(),
                parcelReq.getDepth(),
                parcelReq.getQuantity()
            );
            parcel.setInsuranceValue(parcelReq.getInsuranceValue());
            parcel.setShipment(savedShipment);
            parcelRepository.save(parcel);
        }

        TrackingHistory history = new TrackingHistory(
            savedShipment,
            "PENDING",
            "Shipment created",
            "Shipment has been created and ready to ship"
        );
        trackingHistoryRepository.save(history);

        return savedShipment;
    }

    @Transactional
    public Shipment updateShipmentStatus(Long id, String status) {
        Shipment shipment = getShipmentById(id);
        
        if (status.equals("DELIVERED")) {
            shipment.setDeliveredDate(LocalDateTime.now());
        }
        
        shipment.setStatus(status);
        Shipment updated = shipmentRepository.save(shipment);

        TrackingHistory history = new TrackingHistory(
            updated,
            status,
            "Status updated",
            "Shipment status changed to " + status
        );
        trackingHistoryRepository.save(history);

        return updated;
    }

    public void deleteShipment(Long id) {
        Shipment shipment = getShipmentById(id);
        shipmentRepository.delete(shipment);
    }
}
