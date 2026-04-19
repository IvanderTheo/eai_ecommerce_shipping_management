package com.example.shipping.service;

import com.example.shipping.dto.ShippingAddressRequest;
import com.example.shipping.entity.ShippingAddress;
import com.example.shipping.repository.ShippingAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ShippingAddressService {

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    public List<ShippingAddress> getAllShippingAddresses() {
        return shippingAddressRepository.findAll();
    }

    public ShippingAddress getShippingAddressById(Long id) {
        return shippingAddressRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Shipping address not found with id: " + id));
    }

    public ShippingAddress createShippingAddress(ShippingAddressRequest request) {
        ShippingAddress address = new ShippingAddress(
            request.getRecipientName(),
            request.getPhone(),
            request.getStreet(),
            request.getCity(),
            request.getProvince(),
            request.getPostalCode()
        );
        address.setNotes(request.getNotes());
        return shippingAddressRepository.save(address);
    }

    public ShippingAddress updateShippingAddress(Long id, ShippingAddressRequest request) {
        ShippingAddress address = getShippingAddressById(id);
        address.setRecipientName(request.getRecipientName());
        address.setPhone(request.getPhone());
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setProvince(request.getProvince());
        address.setPostalCode(request.getPostalCode());
        address.setNotes(request.getNotes());
        return shippingAddressRepository.save(address);
    }

    public void deleteShippingAddress(Long id) {
        ShippingAddress address = getShippingAddressById(id);
        shippingAddressRepository.delete(address);
    }
}
