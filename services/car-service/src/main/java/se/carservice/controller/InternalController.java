package se.carservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.carservice.dto.response.CarResponse;
import se.carservice.service.CarService;

@RestController
@RequestMapping("/api/v1/internal")
@RequiredArgsConstructor
public class InternalController {

    private final CarService carService;

    @GetMapping("/cars/{id}")
    public ResponseEntity<CarResponse> getCarById(@PathVariable Integer id) {
        CarResponse car = carService.getCarById(id);
        return ResponseEntity.ok(car);
    }

    @PatchMapping("/cars/{id}/status")
    public ResponseEntity<Void> updateCarStatus(
            @PathVariable Integer id, 
            @RequestParam Byte status) {
        carService.updateCarStatus(id, status);
        return ResponseEntity.ok().build();
    }
}
