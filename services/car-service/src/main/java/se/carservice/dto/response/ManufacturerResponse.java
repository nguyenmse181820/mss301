package se.carservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ManufacturerResponse {
    private Integer manufacturerId;
    private String manufacturerName;
    private String description;
    private String manufacturerCountry;
}
