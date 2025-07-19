package se.rentingservice.pojo;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "RentingTransaction")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentingTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RentingTransactionID")
    private Integer rentingTransactionId;

    @Column(name = "RentingDate")
    private LocalDate rentingDate;

    @Column(name = "TotalPrice", precision = 19, scale = 4)
    private BigDecimal totalPrice;

    @Column(name = "CustomerID", nullable = false)
    private Integer customerId;

    @Column(name = "RentingStatus")
    private Byte rentingStatus; // 1=active, 2=completed, 3=cancelled

    @OneToMany(mappedBy = "rentingTransaction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RentingDetail> rentingDetails;
}
