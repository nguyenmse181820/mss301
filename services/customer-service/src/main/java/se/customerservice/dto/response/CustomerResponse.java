package se.customerservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerResponse {
    private Integer customerId;
    private String customerName;
    private String telephone;
    private String email;
    private LocalDate customerBirthday;
    private Boolean customerStatus;
}
