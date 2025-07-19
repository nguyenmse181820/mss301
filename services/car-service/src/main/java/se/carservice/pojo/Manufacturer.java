package se.carservice.pojo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Manufacturer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ManufacturerID")
    private Integer manufacturerId;

    @Column(name = "ManufacturerName", length = 50, nullable = false)
    private String manufacturerName;

    @Column(name = "Description", length = 250)
    private String description;

    @Column(name = "ManufacturerCountry", length = 50)
    private String manufacturerCountry;
}
