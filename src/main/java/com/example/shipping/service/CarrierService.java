package com.example.shipping.service;

import com.example.shipping.dto.CarrierRequest;
import com.example.shipping.entity.Carrier;
import com.example.shipping.repository.CarrierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarrierService {

    @Autowired
    private CarrierRepository carrierRepository;

    public List<Carrier> getAllCarriers() {
        return carrierRepository.findAll();
    }

    public Carrier getCarrierById(Long id) {
        return carrierRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Carrier not found with id: " + id));
    }

    public Carrier createCarrier(CarrierRequest request) {
        Carrier carrier = new Carrier(request.getName(), request.getCode(), request.getCostPerKg(), request.getEstimatedDays());
        carrier.setDescription(request.getDescription());
        return carrierRepository.save(carrier);
    }

    public Carrier updateCarrier(Long id, CarrierRequest request) {
        Carrier carrier = getCarrierById(id);
        carrier.setName(request.getName());
        carrier.setCode(request.getCode());
        carrier.setCostPerKg(request.getCostPerKg());
        carrier.setEstimatedDays(request.getEstimatedDays());
        carrier.setDescription(request.getDescription());
        return carrierRepository.save(carrier);
    }

    public void deleteCarrier(Long id) {
        Carrier carrier = getCarrierById(id);
        carrierRepository.delete(carrier);
    }
}
