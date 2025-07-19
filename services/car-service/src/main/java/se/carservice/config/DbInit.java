package se.carservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.carservice.pojo.CarInformation;
import se.carservice.pojo.Manufacturer;
import se.carservice.pojo.Supplier;
import se.carservice.repository.CarInformationRepository;
import se.carservice.repository.ManufacturerRepository;
import se.carservice.repository.SupplierRepository;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class DbInit implements CommandLineRunner {

    private final ManufacturerRepository manufacturerRepository;
    private final SupplierRepository supplierRepository;
    private final CarInformationRepository carRepository;

    @Override
    public void run(String... args) throws Exception {
        if (manufacturerRepository.count() == 0) {
            log.info("Initializing database with sample data...");
            initializeData();
            log.info("Database initialization completed!");
        } else {
            log.info("Database already contains data, skipping initialization.");
        }
    }

    private void initializeData() {
        // Create Manufacturers
        Manufacturer toyota = manufacturerRepository.save(Manufacturer.builder()
                .manufacturerName("Toyota")
                .description("Japanese multinational automotive manufacturer")
                .manufacturerCountry("Japan")
                .build());

        Manufacturer honda = manufacturerRepository.save(Manufacturer.builder()
                .manufacturerName("Honda")
                .description("Japanese public multinational conglomerate manufacturer")
                .manufacturerCountry("Japan")
                .build());

        Manufacturer bmw = manufacturerRepository.save(Manufacturer.builder()
                .manufacturerName("BMW")
                .description("German multinational corporation which produces luxury vehicles")
                .manufacturerCountry("Germany")
                .build());

        Manufacturer mercedes = manufacturerRepository.save(Manufacturer.builder()
                .manufacturerName("Mercedes-Benz")
                .description("German global automobile marque and a division of Daimler AG")
                .manufacturerCountry("Germany")
                .build());

        Manufacturer ford = manufacturerRepository.save(Manufacturer.builder()
                .manufacturerName("Ford")
                .description("American multinational automaker")
                .manufacturerCountry("USA")
                .build());

        // Create Suppliers
        Supplier premiumRentals = supplierRepository.save(Supplier.builder()
                .supplierName("Premium Car Rentals")
                .supplierDescription("High-end luxury car rental supplier")
                .supplierAddress("123 Premium Street, Stockholm, Sweden")
                .build());

        Supplier economyFleet = supplierRepository.save(Supplier.builder()
                .supplierName("Economy Fleet Solutions")
                .supplierDescription("Affordable car rental solutions for everyone")
                .supplierAddress("456 Budget Avenue, Gothenburg, Sweden")
                .build());

        Supplier businessCars = supplierRepository.save(Supplier.builder()
                .supplierName("Business Car Services")
                .supplierDescription("Professional car rental for business needs")
                .supplierAddress("789 Corporate Blvd, Malmö, Sweden")
                .build());

        // Create Cars
        // Toyota Cars
        carRepository.save(CarInformation.builder()
                .carName("Toyota Camry")
                .carDescription("Reliable mid-size sedan perfect for business trips")
                .numberOfDoors(4)
                .seatingCapacity(5)
                .fuelType("Gasoline")
                .year(2023)
                .carStatus((byte) 1) // Available
                .carRentingPricePerDay(new BigDecimal("450.00"))
                .manufacturer(toyota)
                .supplier(businessCars)
                .build());

        carRepository.save(CarInformation.builder()
                .carName("Toyota Corolla")
                .carDescription("Compact and fuel-efficient car for city driving")
                .numberOfDoors(4)
                .seatingCapacity(5)
                .fuelType("Hybrid")
                .year(2024)
                .carStatus((byte) 1) // Available
                .carRentingPricePerDay(new BigDecimal("350.00"))
                .manufacturer(toyota)
                .supplier(economyFleet)
                .build());

        carRepository.save(CarInformation.builder()
                .carName("Toyota RAV4")
                .carDescription("Versatile SUV perfect for family adventures")
                .numberOfDoors(5)
                .seatingCapacity(7)
                .fuelType("Hybrid")
                .year(2023)
                .carStatus((byte) 1) // Available
                .carRentingPricePerDay(new BigDecimal("550.00"))
                .manufacturer(toyota)
                .supplier(premiumRentals)
                .build());

        // Honda Cars
        carRepository.save(CarInformation.builder()
                .carName("Honda Civic")
                .carDescription("Sporty and reliable compact car")
                .numberOfDoors(4)
                .seatingCapacity(5)
                .fuelType("Gasoline")
                .year(2023)
                .carStatus((byte) 1) // Available
                .carRentingPricePerDay(new BigDecimal("380.00"))
                .manufacturer(honda)
                .supplier(economyFleet)
                .build());

        carRepository.save(CarInformation.builder()
                .carName("Honda Accord")
                .carDescription("Premium sedan with advanced safety features")
                .numberOfDoors(4)
                .seatingCapacity(5)
                .fuelType("Hybrid")
                .year(2024)
                .carStatus((byte) 1) // Available
                .carRentingPricePerDay(new BigDecimal("480.00"))
                .manufacturer(honda)
                .supplier(businessCars)
                .build());

        carRepository.save(CarInformation.builder()
                .carName("Honda CR-V")
                .carDescription("Spacious SUV with excellent fuel economy")
                .numberOfDoors(5)
                .seatingCapacity(7)
                .fuelType("Hybrid")
                .year(2023)
                .carStatus((byte) 0) // Rented
                .carRentingPricePerDay(new BigDecimal("520.00"))
                .manufacturer(honda)
                .supplier(premiumRentals)
                .build());

        // BMW Cars
        carRepository.save(CarInformation.builder()
                .carName("BMW 3 Series")
                .carDescription("Luxury sedan combining performance and comfort")
                .numberOfDoors(4)
                .seatingCapacity(5)
                .fuelType("Gasoline")
                .year(2024)
                .carStatus((byte) 1) // Available
                .carRentingPricePerDay(new BigDecimal("750.00"))
                .manufacturer(bmw)
                .supplier(premiumRentals)
                .build());

        carRepository.save(CarInformation.builder()
                .carName("BMW X5")
                .carDescription("Premium SUV with cutting-edge technology")
                .numberOfDoors(5)
                .seatingCapacity(7)
                .fuelType("Gasoline")
                .year(2023)
                .carStatus((byte) 1) // Available
                .carRentingPricePerDay(new BigDecimal("950.00"))
                .manufacturer(bmw)
                .supplier(premiumRentals)
                .build());

        carRepository.save(CarInformation.builder()
                .carName("BMW i4")
                .carDescription("Electric luxury sedan for eco-conscious drivers")
                .numberOfDoors(4)
                .seatingCapacity(5)
                .fuelType("Electric")
                .year(2024)
                .carStatus((byte) 2) // Maintenance
                .carRentingPricePerDay(new BigDecimal("850.00"))
                .manufacturer(bmw)
                .supplier(premiumRentals)
                .build());

        // Mercedes-Benz Cars
        carRepository.save(CarInformation.builder()
                .carName("Mercedes C-Class")
                .carDescription("Elegant luxury sedan with premium amenities")
                .numberOfDoors(4)
                .seatingCapacity(5)
                .fuelType("Gasoline")
                .year(2024)
                .carStatus((byte) 1) // Available
                .carRentingPricePerDay(new BigDecimal("800.00"))
                .manufacturer(mercedes)
                .supplier(premiumRentals)
                .build());

        carRepository.save(CarInformation.builder()
                .carName("Mercedes GLC")
                .carDescription("Sophisticated SUV with advanced driver assistance")
                .numberOfDoors(5)
                .seatingCapacity(5)
                .fuelType("Gasoline")
                .year(2023)
                .carStatus((byte) 0) // Rented
                .carRentingPricePerDay(new BigDecimal("900.00"))
                .manufacturer(mercedes)
                .supplier(premiumRentals)
                .build());

        carRepository.save(CarInformation.builder()
                .carName("Mercedes EQS")
                .carDescription("Ultra-luxury electric sedan with exceptional range")
                .numberOfDoors(4)
                .seatingCapacity(5)
                .fuelType("Electric")
                .year(2024)
                .carStatus((byte) 1) // Available
                .carRentingPricePerDay(new BigDecimal("1200.00"))
                .manufacturer(mercedes)
                .supplier(premiumRentals)
                .build());

        // Ford Cars
        carRepository.save(CarInformation.builder()
                .carName("Ford Focus")
                .carDescription("Compact car with great handling and efficiency")
                .numberOfDoors(4)
                .seatingCapacity(5)
                .fuelType("Gasoline")
                .year(2023)
                .carStatus((byte) 1) // Available
                .carRentingPricePerDay(new BigDecimal("320.00"))
                .manufacturer(ford)
                .supplier(economyFleet)
                .build());

        carRepository.save(CarInformation.builder()
                .carName("Ford Explorer")
                .carDescription("Family-friendly SUV with spacious interior")
                .numberOfDoors(5)
                .seatingCapacity(7)
                .fuelType("Gasoline")
                .year(2023)
                .carStatus((byte) 1) // Available
                .carRentingPricePerDay(new BigDecimal("580.00"))
                .manufacturer(ford)
                .supplier(businessCars)
                .build());

        carRepository.save(CarInformation.builder()
                .carName("Ford Mustang")
                .carDescription("Iconic sports car for thrilling driving experience")
                .numberOfDoors(2)
                .seatingCapacity(4)
                .fuelType("Gasoline")
                .year(2024)
                .carStatus((byte) 1) // Available
                .carRentingPricePerDay(new BigDecimal("700.00"))
                .manufacturer(ford)
                .supplier(premiumRentals)
                .build());

        log.info("Created {} manufacturers", manufacturerRepository.count());
        log.info("Created {} suppliers", supplierRepository.count());
        log.info("Created {} cars", carRepository.count());
    }
}
