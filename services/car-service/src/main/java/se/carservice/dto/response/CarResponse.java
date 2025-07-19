package se.carservice.dto.response;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class CarResponse {
    private Integer carId;
    private String carName;
    private String carDescription;
    private Integer numberOfDoors;
    private Integer seatingCapacity;
    private String fuelType;
    private Integer year;
    private String carStatus;
    private BigDecimal carRentingPricePerDay;
    private String manufacturerName;
    private String supplierName;
    private Integer manufacturerId;
    private Integer supplierId;
}
