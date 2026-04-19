package com.example.shipping.util;

import com.example.shipping.dto.*;
import com.example.shipping.entity.*;
import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {

    public static ShipmentResponse toShipmentResponse(Shipment shipment) {
        if (shipment == null) return null;

        ShipmentResponse response = new ShipmentResponse();
        response.setId(shipment.getId());
        response.setTrackingNumber(shipment.getTrackingNumber());
        response.setCarrier(toCarrierResponse(shipment.getCarrier()));
        response.setShippingAddress(toShippingAddressResponse(shipment.getShippingAddress()));
        response.setStatus(shipment.getStatus());
        response.setTotalWeight(shipment.getTotalWeight());
        response.setShippingCost(shipment.getShippingCost());
        response.setShippedDate(shipment.getShippedDate());
        response.setEstimatedDelivery(shipment.getEstimatedDelivery());
        response.setDeliveredDate(shipment.getDeliveredDate());
        
        if (shipment.getParcels() != null) {
            response.setParcels(shipment.getParcels().stream()
                    .map(DtoMapper::toParcelResponse)
                    .collect(Collectors.toList()));
        }
        
        return response;
    }

    public static List<ShipmentResponse> toShipmentResponseList(List<Shipment> shipments) {
        if (shipments == null) return null;
        return shipments.stream()
                .map(DtoMapper::toShipmentResponse)
                .collect(Collectors.toList());
    }

    public static CarrierResponse toCarrierResponse(Carrier carrier) {
        if (carrier == null) return null;

        CarrierResponse response = new CarrierResponse();
        response.setId(carrier.getId());
        response.setName(carrier.getName());
        response.setCode(carrier.getCode());
        response.setDescription(carrier.getDescription());
        response.setCostPerKg(carrier.getCostPerKg());
        response.setEstimatedDays(carrier.getEstimatedDays());
        response.setActive(carrier.getActive());
        
        return response;
    }

    public static List<CarrierResponse> toCarrierResponseList(List<Carrier> carriers) {
        if (carriers == null) return null;
        return carriers.stream()
                .map(DtoMapper::toCarrierResponse)
                .collect(Collectors.toList());
    }

    public static ShippingAddressResponse toShippingAddressResponse(ShippingAddress address) {
        if (address == null) return null;

        ShippingAddressResponse response = new ShippingAddressResponse();
        response.setId(address.getId());
        response.setRecipientName(address.getRecipientName());
        response.setPhone(address.getPhone());
        response.setStreet(address.getStreet());
        response.setCity(address.getCity());
        response.setProvince(address.getProvince());
        response.setPostalCode(address.getPostalCode());
        response.setNotes(address.getNotes());
        
        return response;
    }

    public static List<ShippingAddressResponse> toShippingAddressResponseList(List<ShippingAddress> addresses) {
        if (addresses == null) return null;
        return addresses.stream()
                .map(DtoMapper::toShippingAddressResponse)
                .collect(Collectors.toList());
    }

    public static ParcelResponse toParcelResponse(Parcel parcel) {
        if (parcel == null) return null;

        ParcelResponse response = new ParcelResponse();
        response.setId(parcel.getId());
        response.setBarcode(parcel.getDescription());
        response.setWeight(parcel.getWeight());
        response.setDescription(parcel.getDescription());
        response.setQuantity(parcel.getQuantity());
        
        return response;
    }

    public static List<ParcelResponse> toParcelResponseList(List<Parcel> parcels) {
        if (parcels == null) return null;
        return parcels.stream()
                .map(DtoMapper::toParcelResponse)
                .collect(Collectors.toList());
    }

    public static TrackingHistoryResponse toTrackingHistoryResponse(TrackingHistory history) {
        if (history == null) return null;

        TrackingHistoryResponse response = new TrackingHistoryResponse();
        response.setId(history.getId());
        response.setStatus(history.getStatus());
        response.setTimestamp(history.getTimestamp());
        response.setLocation(history.getLocation());
        response.setDescription(history.getDescription());
        
        return response;
    }

    public static List<TrackingHistoryResponse> toTrackingHistoryResponseList(List<TrackingHistory> histories) {
        if (histories == null) return null;
        return histories.stream()
                .map(DtoMapper::toTrackingHistoryResponse)
                .collect(Collectors.toList());
    }
}
