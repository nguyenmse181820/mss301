package se.carservice.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Supplier")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SupplierID")
    private Integer supplierId;

    @Column(name = "SupplierName", length = 50, nullable = false)
    private String supplierName;

    @Column(name = "SupplierDescription", length = 250)
    private String supplierDescription;

    @Column(name = "SupplierAddress", length = 80)
    private String supplierAddress;
}
