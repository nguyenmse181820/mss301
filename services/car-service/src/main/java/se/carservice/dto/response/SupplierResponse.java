package se.carservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplierResponse {
    private Integer supplierId;
    private String supplierName;
    private String supplierDescription;
    private String supplierAddress;
}
