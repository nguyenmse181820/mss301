package se.carservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.carservice.pojo.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    boolean existsBySupplierName(String supplierName);
}
