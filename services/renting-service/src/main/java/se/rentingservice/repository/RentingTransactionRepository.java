package se.rentingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.rentingservice.pojo.RentingTransaction;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentingTransactionRepository extends JpaRepository<RentingTransaction, Integer> {
    
    List<RentingTransaction> findByCustomerIdOrderByRentingDateDesc(Integer customerId);
    
    List<RentingTransaction> findByRentingDateBetween(LocalDate startDate, LocalDate endDate);
}
