package se.carservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SupplierRequest {
    @NotBlank(message = "Supplier name is required")
    @Size(max = 50, message = "Supplier name must not exceed 50 characters")
    private String supplierName;

    @Size(max = 250, message = "Description must not exceed 250 characters")
    private String supplierDescription;

    @Size(max = 80, message = "Address must not exceed 80 characters")
    private String supplierAddress;
}
