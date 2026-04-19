# API Response Fix - Summary

## Masalah yang Ditemukan
1. **Circular References** - Entities memiliki referensi bidireksional yang menyebabkan serialisasi berulang
   - Carrier → Shipments → Carrier (loop)
   - Shipment → TrackingHistories → Shipment (loop)
   - Parcel → Shipment → Parcel (loop)

2. **No Pagination** - Endpoint `/api/shipments` mengembalikan SEMUA data sekaligus tanpa pagination

3. **Uncontrolled Serialization** - API mengembalikan raw entities yang serialize ALL fields termasuk nested relationships

## Solusi yang Diterapkan

### 1. Tambah @JsonBackReference pada Entities
**File yang diubah:**
- `Shipment.java` - Added `@JsonBackReference` ke trackingHistories
- `Carrier.java` - Added `@JsonBackReference` ke shipments  
- `Parcel.java` - Added `@JsonBackReference` ke shipment
- `TrackingHistory.java` - Added `@JsonBackReference` ke shipment

**Efek:** Mencegah serialisasi bidirectional yang menyebabkan loop

### 2. Buat Response DTOs
**File baru yang dibuat:**
- `ApiResponse.java` - Standard wrapper untuk semua API responses
- `ShipmentResponse.java` - DTO untuk Shipment responses
- `CarrierResponse.java` - DTO untuk Carrier responses
- `ShippingAddressResponse.java` - DTO untuk ShippingAddress responses
- `ParcelResponse.java` - DTO untuk Parcel responses
- `TrackingHistoryResponse.java` - DTO untuk TrackingHistory responses

**Efek:** Response hanya berisi field yang diperlukan, tidak semua nested data

### 3. Buat DtoMapper Utility
**File baru:** `DtoMapper.java`
- Utility class untuk convert entities ke DTOs
- Methods: toShipmentResponse(), toCarrierResponse(), dll
- Semua method menangani null values dengan aman

### 4. Update ShipmentService
**Perubahan:**
- Added `getAllShipmentsPaginated(Pageable pageable)` method
- Support pagination dengan Spring Data

### 5. Update Controllers
**File yang diubah:**
- `ShipmentController.java` 
  - Added pagination support (page, size parameters)
  - Wrap semua responses dengan `ApiResponse<T>`
  - Convert entities ke DTOs before returning
  
- `CarrierController.java`
  - Wrap semua responses dengan `ApiResponse<T>`
  - Convert entities ke DTOs
  
- `ShippingAddressController.java`
  - Wrap semua responses dengan `ApiResponse<T>`
  - Convert entities ke DTOs

## Hasil API Response Baru

### Format Response Standar
```json
{
  "success": true,
  "message": "Shipments retrieved successfully",
  "statusCode": 200,
  "data": {
    "content": [...],
    "totalElements": 50,
    "totalPages": 5,
    "currentPage": 0,
    "pageSize": 10
  }
}
```

### Contoh GET /api/shipments?page=0&size=10
```json
{
  "success": true,
  "message": "Shipments retrieved successfully",
  "statusCode": 200,
  "data": {
    "content": [
      {
        "id": 1,
        "trackingNumber": "SHIP...",
        "status": "PENDING",
        "totalWeight": 5.0,
        "shippingCost": 50000,
        "carrier": {
          "id": 1,
          "name": "JNE",
          "code": "JNE001",
          "costPerKg": 10000
        },
        "shippingAddress": {...},
        "parcels": [...]
      }
    ],
    "totalElements": 50,
    "totalPages": 5,
    "currentPage": 0,
    "pageSize": 10
  }
}
```

## Keuntungan
✅ Response lebih ringkas dan terstruktur
✅ Tidak ada circular reference errors
✅ Pagination untuk handle banyak data
✅ Standard response format untuk semua endpoints
✅ Lebih mudah di-consume oleh frontend
✅ Performa lebih baik (less data transferred)
✅ Flexibility untuk customize response fields

## Testing
Aplikasi sudah dikompilasi dengan sukses dan running di port 8085.

Coba endpoint:
- GET /api/shipments?page=0&size=10
- GET /api/shipments/{id}
- GET /api/shipments/track/{trackingNumber}
- GET /api/carriers
- GET /api/shipping-addresses
