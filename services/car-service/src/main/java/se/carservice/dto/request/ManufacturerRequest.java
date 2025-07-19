package se.carservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ManufacturerRequest {
    @NotBlank(message = "Manufacturer name is required")
    @Size(max = 50, message = "Manufacturer name must not exceed 50 characters")
    private String manufacturerName;

    @Size(max = 250, message = "Description must not exceed 250 characters")
    private String description;

    @Size(max = 10, message = "Country must not exceed 10 characters")
    private String manufacturerCountry;
}
