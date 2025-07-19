package se.rentingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import se.rentingservice.client.fallback.CarServiceFallback;
import se.rentingservice.dto.external.CarResponse;

@FeignClient(
    name = "car-service", 
    path = "/api/v1/internal",
    fallback = CarServiceFallback.class
)
public interface CarServiceClient {

    @GetMapping("/cars/{carId}")
    CarResponse getCarById(@PathVariable("carId") Integer carId);
    
    @PatchMapping("/cars/{carId}/status")
    void updateCarStatus(@PathVariable("carId") Integer carId, @RequestParam("status") Byte status);
}
