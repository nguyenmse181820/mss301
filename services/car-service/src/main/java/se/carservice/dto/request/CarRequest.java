package se.carservice.dto.request;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CarRequest {
    @NotBlank(message = "Car name is required")
    @Size(max = 50, message = "Car name must not exceed 50 characters")
    private String carName;

    @Size(max = 220, message = "Description must not exceed 220 characters")
    private String carDescription;

    @Min(value = 2, message = "Number of doors must be at least 2")
    @Max(value = 6, message = "Number of doors must not exceed 6")
    private Integer numberOfDoors;

    @Min(value = 1, message = "Seating capacity must be at least 1")
    @Max(value = 50, message = "Seating capacity must not exceed 50")
    private Integer seatingCapacity;

    @Size(max = 20, message = "Fuel type must not exceed 20 characters")
    private String fuelType;

    @Min(value = 1900, message = "Year must be at least 1900")
    @Max(value = 2030, message = "Year must not exceed 2030")
    private Integer year;

    @NotNull(message = "Car status is required")
    @Min(value = 0, message = "Car status must be 0, 1, or 2")
    @Max(value = 2, message = "Car status must be 0, 1, or 2")
    private Byte carStatus;

    @NotNull(message = "Renting price is required")
    @DecimalMin(value = "0.0", message = "Price must be positive")
    private BigDecimal carRentingPricePerDay;

    @NotNull(message = "Manufacturer ID is required")
    private Integer manufacturerId;

    @NotNull(message = "Supplier ID is required")
    private Integer supplierId;
}
