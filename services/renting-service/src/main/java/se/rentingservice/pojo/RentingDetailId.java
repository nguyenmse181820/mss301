package se.rentingservice.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentingDetailId implements Serializable {

    @Column(name = "RentingTransactionID")
    private Integer rentingTransactionId;

    @Column(name = "CarID")
    private Integer carId;
}
