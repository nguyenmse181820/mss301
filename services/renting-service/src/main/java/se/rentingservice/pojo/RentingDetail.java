package se.rentingservice.pojo;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "RentingDetail")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentingDetail {

    @EmbeddedId
    private RentingDetailId id;

    @Column(name = "StartDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "EndDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "Price", precision = 19, scale = 4)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RentingTransactionID", insertable = false, updatable = false)
    private RentingTransaction rentingTransaction;

}
