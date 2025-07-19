package se.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.customerservice.pojo.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);
}
