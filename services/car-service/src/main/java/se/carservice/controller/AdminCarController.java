package se.carservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.carservice.dto.request.CarRequest;
import se.carservice.dto.response.ApiResponse;
import se.carservice.dto.response.CarResponse;
import se.carservice.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/cars")
@RequiredArgsConstructor
public class AdminCarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<ApiResponse<CarResponse>> createCar(
            @Valid @RequestBody CarRequest request,
            @RequestHeader("X-User-Email") String userEmail) {
        CarResponse car = carService.createCar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<CarResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Car created successfully")
                        .data(car)
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CarResponse>>> getAllCars(
            @RequestHeader("X-User-Email") String userEmail) {
        List<CarResponse> cars = carService.getAllCars();
        return ResponseEntity.ok(
                ApiResponse.<List<CarResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Cars retrieved successfully")
                        .data(cars)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CarResponse>> getCar(
            @PathVariable Integer id,
            @RequestHeader("X-User-Email") String userEmail) {
        CarResponse car = carService.getCarById(id);
        return ResponseEntity.ok(
                ApiResponse.<CarResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Car retrieved successfully")
                        .data(car)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CarResponse>> updateCar(
            @PathVariable Integer id,
            @Valid @RequestBody CarRequest request,
            @RequestHeader("X-User-Email") String userEmail) {
        CarResponse car = carService.updateCar(id, request);
        return ResponseEntity.ok(
                ApiResponse.<CarResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Car updated successfully")
                        .data(car)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCar(
            @PathVariable Integer id,
            @RequestHeader("X-User-Email") String userEmail) {
        carService.deleteCar(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Car deleted successfully")
                        .build()
        );
    }

    @GetMapping("/manufacturer/{manufacturerId}")
    public ResponseEntity<ApiResponse<List<CarResponse>>> getCarsByManufacturer(
            @PathVariable Integer manufacturerId,
            @RequestHeader("X-User-Email") String userEmail) {
        List<CarResponse> cars = carService.getCarsByManufacturer(manufacturerId);
        return ResponseEntity.ok(
                ApiResponse.<List<CarResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Cars by manufacturer retrieved successfully")
                        .data(cars)
                        .build()
        );
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<ApiResponse<List<CarResponse>>> getCarsBySupplier(
            @PathVariable Integer supplierId,
            @RequestHeader("X-User-Email") String userEmail) {
        List<CarResponse> cars = carService.getCarsBySupplier(supplierId);
        return ResponseEntity.ok(
                ApiResponse.<List<CarResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Cars by supplier retrieved successfully")
                        .data(cars)
                        .build()
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<CarResponse>> updateCarStatus(
            @PathVariable Integer id,
            @RequestParam Byte status,
            @RequestHeader("X-User-Email") String userEmail) {
        CarResponse car = carService.updateCarStatus(id, status);
        return ResponseEntity.ok(
                ApiResponse.<CarResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Car status updated successfully")
                        .data(car)
                        .build()
        );
    }
}
