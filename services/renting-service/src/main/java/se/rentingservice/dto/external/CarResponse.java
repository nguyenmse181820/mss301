package se.rentingservice.dto.external;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CarResponse {
    private Integer carId;
    private String carName;
    private String carStatus;
    private BigDecimal carRentingPricePerDay;
}
