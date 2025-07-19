package se.customerservice.dto.request;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ProfileUpdateRequest {
    @Size(max = 50, message = "Customer name must not exceed 50 characters")
    private String customerName;

    @Size(max = 12, message = "Telephone must not exceed 12 characters")
    private String telephone;

    @Past(message = "Birthday must be in the past")
    private LocalDate customerBirthday;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}