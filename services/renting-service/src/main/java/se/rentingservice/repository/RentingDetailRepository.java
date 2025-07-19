package se.rentingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.rentingservice.pojo.RentingDetail;
import se.rentingservice.pojo.RentingDetailId;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentingDetailRepository extends JpaRepository<RentingDetail, RentingDetailId> {
    List<RentingDetail> findByIdCarIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Integer carId, LocalDate endDate, LocalDate startDate);
}
