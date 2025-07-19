package se.carservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.carservice.dto.request.SupplierRequest;
import se.carservice.dto.response.ApiResponse;
import se.carservice.dto.response.SupplierResponse;
import se.carservice.service.SupplierService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/suppliers")
@RequiredArgsConstructor
public class AdminSupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<ApiResponse<SupplierResponse>> createSupplier(
            @Valid @RequestBody SupplierRequest request,
            @RequestHeader("X-User-Email") String userEmail) {
        SupplierResponse supplier = supplierService.createSupplier(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<SupplierResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Supplier created successfully")
                        .data(supplier)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SupplierResponse>>> getAllSuppliers(
            @RequestHeader("X-User-Email") String userEmail) {
        List<SupplierResponse> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(
                ApiResponse.<List<SupplierResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Suppliers retrieved successfully")
                        .data(suppliers)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierResponse>> getSupplier(
            @PathVariable Integer id,
            @RequestHeader("X-User-Email") String userEmail) {
        SupplierResponse supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok(
                ApiResponse.<SupplierResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Supplier retrieved successfully")
                        .data(supplier)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierResponse>> updateSupplier(
            @PathVariable Integer id,
            @Valid @RequestBody SupplierRequest request,
            @RequestHeader("X-User-Email") String userEmail) {
        SupplierResponse supplier = supplierService.updateSupplier(id, request);
        return ResponseEntity.ok(
                ApiResponse.<SupplierResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Supplier updated successfully")
                        .data(supplier)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSupplier(
            @PathVariable Integer id,
            @RequestHeader("X-User-Email") String userEmail) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Supplier deleted successfully")
                        .build()
        );
    }
}