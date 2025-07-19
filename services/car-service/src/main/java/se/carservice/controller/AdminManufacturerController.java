package se.carservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.carservice.dto.request.ManufacturerRequest;
import se.carservice.dto.response.ApiResponse;
import se.carservice.dto.response.ManufacturerResponse;
import se.carservice.service.ManufacturerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/manufacturers")
@RequiredArgsConstructor
public class AdminManufacturerController {

    private final ManufacturerService manufacturerService;

    @PostMapping
    public ResponseEntity<ApiResponse<ManufacturerResponse>> createManufacturer(
            @Valid @RequestBody ManufacturerRequest request,
            @RequestHeader("X-User-Email") String userEmail) {
        ManufacturerResponse manufacturer = manufacturerService.createManufacturer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<ManufacturerResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Manufacturer created successfully")
                        .data(manufacturer)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ManufacturerResponse>>> getAllManufacturers(
            @RequestHeader("X-User-Email") String userEmail) {
        List<ManufacturerResponse> manufacturers = manufacturerService.getAllManufacturers();
        return ResponseEntity.ok(
                ApiResponse.<List<ManufacturerResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Manufacturers retrieved successfully")
                        .data(manufacturers)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ManufacturerResponse>> getManufacturer(
            @PathVariable Integer id,
            @RequestHeader("X-User-Email") String userEmail) {
        ManufacturerResponse manufacturer = manufacturerService.getManufacturerById(id);
        return ResponseEntity.ok(
                ApiResponse.<ManufacturerResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Manufacturer retrieved successfully")
                        .data(manufacturer)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ManufacturerResponse>> updateManufacturer(
            @PathVariable Integer id,
            @Valid @RequestBody ManufacturerRequest request,
            @RequestHeader("X-User-Email") String userEmail) {
        ManufacturerResponse manufacturer = manufacturerService.updateManufacturer(id, request);
        return ResponseEntity.ok(
                ApiResponse.<ManufacturerResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Manufacturer updated successfully")
                        .data(manufacturer)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteManufacturer(
            @PathVariable Integer id,
            @RequestHeader("X-User-Email") String userEmail) {
        manufacturerService.deleteManufacturer(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Manufacturer deleted successfully")
                        .build()
        );
    }
}
