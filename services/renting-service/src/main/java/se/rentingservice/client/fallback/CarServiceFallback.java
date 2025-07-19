package se.rentingservice.client.fallback;

import org.springframework.stereotype.Component;
import se.rentingservice.client.CarServiceClient;
import se.rentingservice.dto.external.CarResponse;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Component
public class CarServiceFallback implements CarServiceClient {

    @Override
    public CarResponse getCarById(Integer carId) {
        CarResponse fallbackCar = new CarResponse();
        fallbackCar.setCarId(carId);
        fallbackCar.setCarName("Service Unavailable");
        fallbackCar.setCarStatus("Unknown");
        fallbackCar.setCarRentingPricePerDay(BigDecimal.ZERO);
        return fallbackCar;
    }

    @Override
    public void updateCarStatus(Integer carId, Byte status) {
        System.err.println("Car service unavailable - cannot update car status for car ID: " + carId);
    }
}
