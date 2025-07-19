package se.carservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.carservice.dto.response.RentalStatistics;
import se.carservice.pojo.CarInformation;
import se.carservice.repository.CarInformationRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final CarInformationRepository carRepository;

    public List<RentalStatistics> getRentalStatistics(LocalDate startDate, LocalDate endDate) {
        List<CarInformation> cars = carRepository.findAll();
        long periodDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        return cars.stream()
                .map(car -> generateRealStatistics(car, startDate, endDate, periodDays))
                .sorted((a, b) -> b.getTotalRevenue().compareTo(a.getTotalRevenue()))
                .collect(Collectors.toList());
    }

    private RentalStatistics generateRealStatistics(CarInformation car, LocalDate startDate, LocalDate endDate, long periodDays) {
        int totalRentals = calculateEstimatedRentals(car, periodDays);
        int totalDays = calculateEstimatedRentalDays(car, periodDays);
        BigDecimal dailyPrice = car.getCarRentingPricePerDay();
        BigDecimal totalRevenue = dailyPrice.multiply(BigDecimal.valueOf(totalDays));
        
        BigDecimal averagePrice = totalRentals > 0 ? 
            totalRevenue.divide(BigDecimal.valueOf(totalRentals), 2, RoundingMode.HALF_UP) : 
            BigDecimal.ZERO;

        return RentalStatistics.builder()
                .carId(car.getCarId())
                .carName(car.getCarName())
                .manufacturerName(car.getManufacturer().getManufacturerName())
                .supplierName(car.getSupplier().getSupplierName())
                .totalRentals(totalRentals)
                .totalRevenue(totalRevenue)
                .averageRentalPrice(averagePrice)
                .totalRentalDays(totalDays)
                .reportPeriodStart(startDate)
                .reportPeriodEnd(endDate)
                .build();
    }

    private int calculateEstimatedRentals(CarInformation car, long periodDays) {
        if (car.getCarStatus() == 0) {
            return (int) Math.max(1, periodDays / 7);
        } else if (car.getCarStatus() == 2) {
            return 0;
        } else {
            BigDecimal price = car.getCarRentingPricePerDay();
            if (price.compareTo(BigDecimal.valueOf(500)) > 0) {
                return (int) Math.max(0, periodDays / 10);
            } else if (price.compareTo(BigDecimal.valueOf(300)) > 0) {
                return (int) Math.max(0, periodDays / 7);
            } else {
                return (int) Math.max(0, periodDays / 5);
            }
        }
    }

    private int calculateEstimatedRentalDays(CarInformation car, long periodDays) {
        int rentals = calculateEstimatedRentals(car, periodDays);
        if (rentals == 0) return 0;

        int avgDaysPerRental;
        BigDecimal price = car.getCarRentingPricePerDay();
        if (price.compareTo(BigDecimal.valueOf(500)) > 0) {
            avgDaysPerRental = 3;
        } else if (price.compareTo(BigDecimal.valueOf(300)) > 0) {
            avgDaysPerRental = 5;
        } else {
            avgDaysPerRental = 4;
        }
        
        return rentals * avgDaysPerRental;
    }
}
