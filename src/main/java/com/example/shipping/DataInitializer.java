package com.example.shipping;

import com.example.shipping.entity.*;
import com.example.shipping.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CarrierRepository carrierRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ParcelRepository parcelRepository;

    @Override
    public void run(String... args) throws Exception {
        if (carrierRepository.count() == 0) {
            Carrier jne = new Carrier("JNE Express", "JNE", 5000.0, 2L);
            jne.setDescription("Jasa pengiriman terpercaya di seluruh Indonesia");
            carrierRepository.save(jne);

            Carrier tiki = new Carrier("TIKI", "TIKI", 4500.0, 3L);
            tiki.setDescription("Pengiriman dengan tracking real-time");
            carrierRepository.save(tiki);

            Carrier pos = new Carrier("Pos Indonesia", "POS", 3000.0, 5L);
            pos.setDescription("Layanan pengiriman resmi pemerintah");
            carrierRepository.save(pos);

            System.out.println("✓ 3 Carriers berhasil ditambahkan");
        }

        if (shippingAddressRepository.count() == 0) {
            ShippingAddress addr1 = new ShippingAddress("Budi Santoso", "081234567890", "Jl. Merdeka No. 123", "Jakarta", "DKI Jakarta", "12345");
            addr1.setNotes("Rumah warna putih, dekat toko kelontong");
            shippingAddressRepository.save(addr1);

            ShippingAddress addr2 = new ShippingAddress("Siti Nurhaliza", "081987654321", "Jl. Ahmad Yani No. 456", "Surabaya", "Jawa Timur", "60123");
            addr2.setNotes("Kantor di lantai 3");
            shippingAddressRepository.save(addr2);

            ShippingAddress addr3 = new ShippingAddress("Rani Wijaya", "082555666777", "Jl. Sudirman No. 789", "Bandung", "Jawa Barat", "40123");
            addr3.setNotes("Rumah kos, tinggal hubungi pemilik");
            shippingAddressRepository.save(addr3);

            System.out.println("✓ 3 Shipping Addresses berhasil ditambahkan");
        }

        if (shipmentRepository.count() == 0) {
            Carrier jne = carrierRepository.findAll().get(0);
            ShippingAddress addr1 = shippingAddressRepository.findAll().get(0);

            Shipment shipment1 = new Shipment("SHIP123ABC45678", jne, addr1, 5.5, 27500.0);
            shipment1.setStatus("IN_TRANSIT");
            shipment1.setEstimatedDelivery(LocalDateTime.now().plusDays(2));
            Shipment savedShipment1 = shipmentRepository.save(shipment1);

            Parcel parcel1 = new Parcel("Laptop Dell", 3.5, 35.0, 25.0, 3.0, 1);
            parcel1.setInsuranceValue(8000000.0);
            parcel1.setShipment(savedShipment1);
            parcelRepository.save(parcel1);

            Parcel parcel2 = new Parcel("Mouse dan Keyboard", 2.0, 20.0, 15.0, 2.0, 2);
            parcel2.setInsuranceValue(500000.0);
            parcel2.setShipment(savedShipment1);
            parcelRepository.save(parcel2);

            System.out.println("✓ 1 Shipment dengan 2 Parcels berhasil ditambahkan");
        }

        System.out.println("═══════════════════════════════════════");
        System.out.println("✓ Shipping Data Initialization Selesai!");
        System.out.println("═══════════════════════════════════════");
    }
}
