package se.customerservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.customerservice.pojo.Customer;
import se.customerservice.pojo.enums.Role;
import se.customerservice.repository.CustomerRepository;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(1) // Run first, before other services
public class DbInit implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (customerRepository.count() == 0) {
            log.info("Initializing customer database with sample data...");
            initializeCustomerData();
            log.info("Customer database initialization completed!");
        } else {
            log.info("Customer database already contains data, skipping initialization.");
        }
    }

    private void initializeCustomerData() {
        // Create sample customers that match the rental data

        // Customer 1 - Regular customer with multiple rentals
        Customer customer1 = Customer.builder()
                .customerName("John Anderson")
                .telephone("073123456789")
                .email("john.anderson@email.com")
                .customerBirthday(LocalDate.of(1985, 3, 15))
                .customerStatus(true)
                .password(passwordEncoder.encode("password123"))
                .role(Role.CUSTOMER)
                .build();

        customerRepository.save(customer1);

        // Customer 2 - Business customer
        Customer customer2 = Customer.builder()
                .customerName("Maria Johansson")
                .telephone("073987654321")
                .email("maria.johansson@business.com")
                .customerBirthday(LocalDate.of(1990, 7, 22))
                .customerStatus(true)
                .password(passwordEncoder.encode("business456"))
                .role(Role.CUSTOMER)
                .build();

        customerRepository.save(customer2);

        // Customer 3 - Occasional renter
        Customer customer3 = Customer.builder()
                .customerName("Erik Lindqvist")
                .telephone("073456789123")
                .email("erik.lindqvist@gmail.com")
                .customerBirthday(LocalDate.of(1988, 11, 8))
                .customerStatus(true)
                .password(passwordEncoder.encode("erik789"))
                .role(Role.CUSTOMER)
                .build();

        customerRepository.save(customer3);

        // Admin user for testing
        Customer admin = Customer.builder()
                .customerName("Admin User")
                .telephone("070000000000")
                .email("admin@fucarrental.com")
                .customerBirthday(LocalDate.of(1980, 1, 1))
                .customerStatus(true)
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .build();

        customerRepository.save(admin);

        // Test user that was registered earlier
        if (!customerRepository.existsByEmail("nguyen@gmail.com")) {
            Customer testUser = Customer.builder()
                    .customerName("Nguyen Moc")
                    .telephone("090909090909")
                    .email("nguyen@gmail.com")
                    .customerBirthday(LocalDate.of(1990, 3, 20))
                    .customerStatus(true)
                    .password(passwordEncoder.encode("1234567"))
                    .role(Role.CUSTOMER)
                    .build();

            customerRepository.save(testUser);
        }

        log.info("Created {} customers", customerRepository.count());
    }
}
