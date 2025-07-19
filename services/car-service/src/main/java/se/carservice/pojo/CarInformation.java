package se.carservice.pojo;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "car_information")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CarID")
    private Integer carId;

    @Column(name = "CarName", length = 50, nullable = false)
    private String carName;

    @Column(name = "CarDescription", length = 220)
    private String carDescription;

    @Column(name = "NumberOfDoors")
    private Integer numberOfDoors;

    @Column(name = "SeatingCapacity")
    private Integer seatingCapacity;

    @Column(name = "FuelType", length = 50)
    private String fuelType;

    @Column(name = "Year")
    private Integer year;

    @Column(name = "CarStatus")
    private Byte carStatus;

    @Column(name = "CarRentingPricePerDay", precision = 19, scale = 4)
    private BigDecimal carRentingPricePerDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ManufacturerID", nullable = false)
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SupplierID", nullable = false)
    private Supplier supplier;
}
