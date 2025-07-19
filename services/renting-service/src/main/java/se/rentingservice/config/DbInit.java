package se.rentingservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import se.rentingservice.pojo.RentingDetail;
import se.rentingservice.pojo.RentingDetailId;
import se.rentingservice.pojo.RentingTransaction;
import se.rentingservice.repository.RentingDetailRepository;
import se.rentingservice.repository.RentingTransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(2) // Run after other initializations
public class DbInit implements CommandLineRunner {

    private final RentingTransactionRepository rentingTransactionRepository;
    private final RentingDetailRepository rentingDetailRepository;

    @Override
    public void run(String... args) throws Exception {
        if (rentingTransactionRepository.count() == 0) {
            log.info("Initializing renting database with sample data...");
            initializeRentalData();
            log.info("Renting database initialization completed!");
        } else {
            log.info("Renting database already contains data, skipping initialization.");
        }
    }

    private void initializeRentalData() {
        // Sample rental transactions
        // Note: Customer IDs should match existing customers in customer-service
        // For demo purposes, using sample customer IDs 1, 2, 3

        // Active Rental 1 - Honda CR-V (Car ID 6, currently rented)
        RentingTransaction activeRental1 = RentingTransaction.builder()
                .rentingDate(LocalDate.now().minusDays(3))
                .totalPrice(new BigDecimal("2080.00")) // 4 days * 520.00
                .customerId(1) // Sample customer ID
                .rentingStatus((byte) 1) // Active
                .build();

        RentingTransaction savedRental1 = rentingTransactionRepository.save(activeRental1);

        RentingDetail rental1Detail = RentingDetail.builder()
                .id(new RentingDetailId(savedRental1.getRentingTransactionId(), 6)) // Honda CR-V
                .startDate(LocalDate.now().minusDays(3))
                .endDate(LocalDate.now().plusDays(1))
                .price(new BigDecimal("2080.00"))
                .rentingTransaction(savedRental1)
                .build();

        rentingDetailRepository.save(rental1Detail);
        savedRental1.setRentingDetails(Arrays.asList(rental1Detail));

        // Active Rental 2 - Mercedes GLC (Car ID 11, currently rented)
        RentingTransaction activeRental2 = RentingTransaction.builder()
                .rentingDate(LocalDate.now().minusDays(5))
                .totalPrice(new BigDecimal("4500.00")) // 5 days * 900.00
                .customerId(2) // Sample customer ID
                .rentingStatus((byte) 1) // Active
                .build();

        RentingTransaction savedRental2 = rentingTransactionRepository.save(activeRental2);

        RentingDetail rental2Detail = RentingDetail.builder()
                .id(new RentingDetailId(savedRental2.getRentingTransactionId(), 11)) // Mercedes GLC
                .startDate(LocalDate.now().minusDays(5))
                .endDate(LocalDate.now().plusDays(2))
                .price(new BigDecimal("4500.00"))
                .rentingTransaction(savedRental2)
                .build();

        rentingDetailRepository.save(rental2Detail);
        savedRental2.setRentingDetails(Arrays.asList(rental2Detail));

        // Completed Rental 1 - BMW 3 Series
        RentingTransaction completedRental1 = RentingTransaction.builder()
                .rentingDate(LocalDate.now().minusDays(10))
                .totalPrice(new BigDecimal("2250.00")) // 3 days * 750.00
                .customerId(1) // Sample customer ID
                .rentingStatus((byte) 2) // Completed
                .build();

        RentingTransaction savedCompleted1 = rentingTransactionRepository.save(completedRental1);

        RentingDetail completed1Detail = RentingDetail.builder()
                .id(new RentingDetailId(savedCompleted1.getRentingTransactionId(), 7)) // BMW 3 Series
                .startDate(LocalDate.now().minusDays(10))
                .endDate(LocalDate.now().minusDays(7))
                .price(new BigDecimal("2250.00"))
                .rentingTransaction(savedCompleted1)
                .build();

        rentingDetailRepository.save(completed1Detail);
        savedCompleted1.setRentingDetails(Arrays.asList(completed1Detail));

        // Completed Rental 2 - Toyota Camry
        RentingTransaction completedRental2 = RentingTransaction.builder()
                .rentingDate(LocalDate.now().minusDays(15))
                .totalPrice(new BigDecimal("1800.00")) // 4 days * 450.00
                .customerId(3) // Sample customer ID
                .rentingStatus((byte) 2) // Completed
                .build();

        RentingTransaction savedCompleted2 = rentingTransactionRepository.save(completedRental2);

        RentingDetail completed2Detail = RentingDetail.builder()
                .id(new RentingDetailId(savedCompleted2.getRentingTransactionId(), 1)) // Toyota Camry
                .startDate(LocalDate.now().minusDays(15))
                .endDate(LocalDate.now().minusDays(11))
                .price(new BigDecimal("1800.00"))
                .rentingTransaction(savedCompleted2)
                .build();

        rentingDetailRepository.save(completed2Detail);
        savedCompleted2.setRentingDetails(Arrays.asList(completed2Detail));

        // Completed Rental 3 - Ford Explorer (Multi-car rental)
        RentingTransaction completedRental3 = RentingTransaction.builder()
                .rentingDate(LocalDate.now().minusDays(20))
                .totalPrice(new BigDecimal("3480.00")) // Ford Explorer: 3 days * 580 + Honda Civic: 3 days * 380
                .customerId(2) // Sample customer ID
                .rentingStatus((byte) 2) // Completed
                .build();

        RentingTransaction savedCompleted3 = rentingTransactionRepository.save(completedRental3);

        // Ford Explorer
        RentingDetail completed3Detail1 = RentingDetail.builder()
                .id(new RentingDetailId(savedCompleted3.getRentingTransactionId(), 14)) // Ford Explorer
                .startDate(LocalDate.now().minusDays(20))
                .endDate(LocalDate.now().minusDays(17))
                .price(new BigDecimal("1740.00")) // 3 days * 580
                .rentingTransaction(savedCompleted3)
                .build();

        // Honda Civic
        RentingDetail completed3Detail2 = RentingDetail.builder()
                .id(new RentingDetailId(savedCompleted3.getRentingTransactionId(), 4)) // Honda Civic
                .startDate(LocalDate.now().minusDays(20))
                .endDate(LocalDate.now().minusDays(17))
                .price(new BigDecimal("1140.00")) // 3 days * 380
                .rentingTransaction(savedCompleted3)
                .build();

        rentingDetailRepository.save(completed3Detail1);
        rentingDetailRepository.save(completed3Detail2);
        savedCompleted3.setRentingDetails(Arrays.asList(completed3Detail1, completed3Detail2));

        // Cancelled Rental
        RentingTransaction cancelledRental = RentingTransaction.builder()
                .rentingDate(LocalDate.now().minusDays(8))
                .totalPrice(new BigDecimal("1600.00")) // 2 days * 800.00
                .customerId(3) // Sample customer ID
                .rentingStatus((byte) 3) // Cancelled
                .build();

        RentingTransaction savedCancelled = rentingTransactionRepository.save(cancelledRental);

        RentingDetail cancelledDetail = RentingDetail.builder()
                .id(new RentingDetailId(savedCancelled.getRentingTransactionId(), 10)) // Mercedes C-Class
                .startDate(LocalDate.now().minusDays(8))
                .endDate(LocalDate.now().minusDays(6))
                .price(new BigDecimal("1600.00"))
                .rentingTransaction(savedCancelled)
                .build();

        rentingDetailRepository.save(cancelledDetail);
        savedCancelled.setRentingDetails(Arrays.asList(cancelledDetail));

        log.info("Created {} rental transactions", rentingTransactionRepository.count());
        log.info("Created {} rental details", rentingDetailRepository.count());
    }
}
