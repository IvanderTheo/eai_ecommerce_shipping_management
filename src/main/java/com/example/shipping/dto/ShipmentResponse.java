package com.example.shipping.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ShipmentResponse {
    private Long id;
    private String trackingNumber;
    private CarrierResponse carrier;
    private ShippingAddressResponse shippingAddress;
    private String status;
    private Double totalWeight;
    private Double shippingCost;
    private LocalDateTime shippedDate;
    private LocalDateTime estimatedDelivery;
    private LocalDateTime deliveredDate;
    private List<ParcelResponse> parcels;

    public ShipmentResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

    public CarrierResponse getCarrier() { return carrier; }
    public void setCarrier(CarrierResponse carrier) { this.carrier = carrier; }

    public ShippingAddressResponse getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(ShippingAddressResponse shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getTotalWeight() { return totalWeight; }
    public void setTotalWeight(Double totalWeight) { this.totalWeight = totalWeight; }

    public Double getShippingCost() { return shippingCost; }
    public void setShippingCost(Double shippingCost) { this.shippingCost = shippingCost; }

    public LocalDateTime getShippedDate() { return shippedDate; }
    public void setShippedDate(LocalDateTime shippedDate) { this.shippedDate = shippedDate; }

    public LocalDateTime getEstimatedDelivery() { return estimatedDelivery; }
    public void setEstimatedDelivery(LocalDateTime estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }

    public LocalDateTime getDeliveredDate() { return deliveredDate; }
    public void setDeliveredDate(LocalDateTime deliveredDate) { this.deliveredDate = deliveredDate; }

    public List<ParcelResponse> getParcels() { return parcels; }
    public void setParcels(List<ParcelResponse> parcels) { this.parcels = parcels; }
}
