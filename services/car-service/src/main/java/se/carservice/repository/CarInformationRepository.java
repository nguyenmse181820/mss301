package se.carservice.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.carservice.pojo.CarInformation;

import java.util.List;

@Repository
public interface CarInformationRepository extends JpaRepository<CarInformation, Integer> {

    List<CarInformation> findByCarStatus(Byte carStatus);

    List<CarInformation> findByManufacturer_ManufacturerId(Integer manufacturerId);

    List<CarInformation> findBySupplier_SupplierId(Integer supplierId);

    boolean existsByCarNameAndManufacturer_ManufacturerId(String carName, Integer manufacturerId);
}
