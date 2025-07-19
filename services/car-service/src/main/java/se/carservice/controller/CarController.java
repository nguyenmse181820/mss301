package se.carservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.carservice.dto.response.ApiResponse;
import se.carservice.dto.response.CarResponse;
import se.carservice.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<CarResponse>>> getAvailableCars(
            @RequestHeader("X-User-Email") String userEmail) {
        List<CarResponse> cars = carService.getAvailableCars();
        return ResponseEntity.ok(
                ApiResponse.<List<CarResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Available cars retrieved successfully")
                        .data(cars)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CarResponse>> getCarDetails(
            @PathVariable Integer id,
            @RequestHeader("X-User-Email") String userEmail) {
        CarResponse car = carService.getCarById(id);
        return ResponseEntity.ok(
                ApiResponse.<CarResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Car details retrieved successfully")
                        .data(car)
                        .build()
        );
    }
}
