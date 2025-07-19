package se.customerservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import se.customerservice.pojo.enums.Role;

import java.time.LocalDate;

@Data
public class CustomerRequest {
    @NotBlank(message = "Customer name is required")
    private String customerName;

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String telephone;

    @Past(message = "Birthday must be in the past")
    private LocalDate customerBirthday;

    @NotNull(message = "Customer status is required")
    private Boolean customerStatus;

    @NotNull(message = "Role is required")
    private Role role;
}