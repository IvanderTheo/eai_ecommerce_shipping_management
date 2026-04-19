package com.example.shipping.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public class ShipmentRequest {

    @NotNull(message = "Carrier ID is required")
    private Long carrierId;

    @NotNull(message = "Shipping address ID is required")
    private Long shippingAddressId;

    @NotEmpty(message = "At least one parcel is required")
    private List<PackageRequest> parcels;

    public ShipmentRequest() {}

    public Long getCarrierId() { return carrierId; }
    public void setCarrierId(Long carrierId) { this.carrierId = carrierId; }

    public Long getShippingAddressId() { return shippingAddressId; }
    public void setShippingAddressId(Long shippingAddressId) { this.shippingAddressId = shippingAddressId; }

    public List<PackageRequest> getParcels() { return parcels; }
    public void setParcels(List<PackageRequest> parcels) { this.parcels = parcels; }
}
