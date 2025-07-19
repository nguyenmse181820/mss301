package se.rentingservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class RentingDetailResponse {
    private Integer carId;
    private String carName;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
}
