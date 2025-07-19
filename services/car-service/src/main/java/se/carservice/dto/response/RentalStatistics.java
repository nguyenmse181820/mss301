package se.carservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class RentalStatistics {
    private Integer carId;
    private String carName;
    private String manufacturerName;
    private String supplierName;
    private Integer totalRentals;
    private BigDecimal totalRevenue;
    private BigDecimal averageRentalPrice;
    private Integer totalRentalDays;
    private LocalDate reportPeriodStart;
    private LocalDate reportPeriodEnd;
}
