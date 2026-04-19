# Shipping Management System

## 📋 Project Overview

Shipping Management System adalah REST API untuk mengelola pengiriman barang dengan tracking real-time, multiple carriers, dan management packaging.

**Database:** `shipping_management_db`  
**Port:** `8085`

---

## 🚀 Entities

### 1. Carrier
- Perusahaan jasa pengiriman (JNE, TIKI, Pos, dll)
- Cost per kg dan estimasi waktu pengiriman

### 2. ShippingAddress
- Alamat tujuan pengiriman
- Nama penerima, telepon, lokasi lengkap

### 3. Parcel
- Paket individual dalam sebuah shipment
- Dimensi (panjang, lebar, tinggi), berat, asuransi

### 4. Shipment
- Pengiriman utama (bisa berisi multiple parcels)
- Tracking number, status, cost calculation
- Relationship: Carrier, ShippingAddress, List<Parcel>

### 5. TrackingHistory
- Riwayat tracking pengiriman
- Status updates dengan timestamp dan lokasi

---

## 🔌 API Endpoints

### Carriers
- `GET /api/carriers` - Get all carriers
- `GET /api/carriers/{id}` - Get carrier by ID
- `POST /api/carriers` - Create new carrier
- `PUT /api/carriers/{id}` - Update carrier
- `DELETE /api/carriers/{id}` - Delete carrier

### Shipping Addresses
- `GET /api/shipping-addresses` - Get all addresses
- `GET /api/shipping-addresses/{id}` - Get address by ID
- `POST /api/shipping-addresses` - Create new address
- `PUT /api/shipping-addresses/{id}` - Update address
- `DELETE /api/shipping-addresses/{id}` - Delete address

### Shipments
- `GET /api/shipments` - Get all shipments
- `GET /api/shipments/{id}` - Get shipment by ID
- `GET /api/shipments/track/{trackingNumber}` - Track shipment
- `POST /api/shipments` - Create new shipment with packages
- `PUT /api/shipments/{id}/status` - Update shipment status
- `DELETE /api/shipments/{id}` - Delete shipment

---

## 💾 Database Schema

```sql
CREATE TABLE carriers (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL UNIQUE,
  code VARCHAR(255) NOT NULL,
  cost_per_kg DOUBLE NOT NULL,
  estimated_days BIGINT NOT NULL,
  description TEXT,
  active BOOLEAN DEFAULT true
);

CREATE TABLE shipping_addresses (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  recipient_name VARCHAR(255) NOT NULL,
  phone VARCHAR(255) NOT NULL,
  street VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  province VARCHAR(255) NOT NULL,
  postal_code VARCHAR(255) NOT NULL,
  notes TEXT
);

CREATE TABLE shipments (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  tracking_number VARCHAR(255) NOT NULL UNIQUE,
  carrier_id BIGINT NOT NULL,
  shipping_address_id BIGINT NOT NULL,
  status VARCHAR(255) NOT NULL,
  total_weight DOUBLE NOT NULL,
  shipping_cost DOUBLE NOT NULL,
  shipped_date DATETIME NOT NULL,
  estimated_delivery DATETIME,
  delivered_date DATETIME,
  FOREIGN KEY (carrier_id) REFERENCES carriers(id),
  FOREIGN KEY (shipping_address_id) REFERENCES shipping_addresses(id)
);

CREATE TABLE parcels (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  shipment_id BIGINT NOT NULL,
  description VARCHAR(255) NOT NULL,
  weight DOUBLE NOT NULL,
  width DOUBLE NOT NULL,
  height DOUBLE NOT NULL,
  depth DOUBLE NOT NULL,
  quantity INT NOT NULL,
  insurance_value DOUBLE,
  FOREIGN KEY (shipment_id) REFERENCES shipments(id)
);

CREATE TABLE tracking_histories (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  shipment_id BIGINT NOT NULL,
  status VARCHAR(255) NOT NULL,
  timestamp DATETIME NOT NULL,
  location TEXT,
  description TEXT,
  FOREIGN KEY (shipment_id) REFERENCES shipments(id)
);
```

---

## 📊 Test Data

**Carriers:** JNE Express, TIKI, Pos Indonesia  
**Shipping Addresses:** 3 alamat tujuan  
**Shipments:** 1 shipment dengan 2 parcels

---

## 🧪 Testing Examples

### Create Carrier
```bash
curl -X POST http://localhost:8085/api/carriers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "GoSend",
    "code": "GOS",
    "costPerKg": 6000,
    "estimatedDays": 1,
    "description": "Pengiriman same-day"
  }'
```

### Create Shipping Address
```bash
curl -X POST http://localhost:8085/api/shipping-addresses \
  -H "Content-Type: application/json" \
  -d '{
    "recipientName": "John Doe",
    "phone": "081234567890",
    "street": "Jl. Merdeka No. 123",
    "city": "Jakarta",
    "province": "DKI Jakarta",
    "postalCode": "12345",
    "notes": "Rumah warna putih"
  }'
```

### Create Shipment
```bash
curl -X POST http://localhost:8085/api/shipments \
  -H "Content-Type: application/json" \
  -d '{
    "carrierId": 1,
    "shippingAddressId": 1,
    "packages": [
      {
        "description": "Laptop Dell",
        "weight": 3.5,
        "width": 35,
        "height": 25,
        "depth": 3,
        "quantity": 1,
        "insuranceValue": 8000000
      }
    ]
  }'
```

### Track Shipment
```bash
curl -X GET "http://localhost:8085/api/shipments/track/SHIP123ABC45678"
```

### Update Shipment Status
```bash
curl -X PUT "http://localhost:8085/api/shipments/1/status?status=DELIVERED"
```

---

## 🎯 Error Handling

All errors return consistent format:
```json
{
  "timestamp": "2026-04-16T10:30:00",
  "status": 400,
  "errors": {
    "fieldName": "Error message"
  }
}
```

---

## 📌 Notes

- Shipment cost calculated automatically: `totalWeight * carrier.costPerKg`
- Tracking number generated automatically with UUID
- Estimated delivery calculated: `current date + carrier.estimatedDays`
- TrackingHistory auto-created on shipment creation
- Validation at DTO layer with custom messages
