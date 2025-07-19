package se.rentingservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class RentingTransactionResponse {
    private Integer rentingTransactionId;
    private LocalDate rentingDate;
    private BigDecimal totalPrice;
    private Integer customerId;
    private String rentingStatus;
    private List<RentingDetailResponse> rentingDetails;
}
