package se.rentingservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RentingTransactionRequest {
    
    @NotEmpty(message = "Renting details are required")
    @Valid
    private List<RentingDetailRequest> rentingDetails;
}
