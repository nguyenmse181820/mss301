package se.rentingservice.dto.external;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CustomerResponse {
    private Integer customerId;
    private String customerName;
    private String email;
    private String telephone;
    private LocalDate customerBirthday;
    private Boolean customerStatus;
}
