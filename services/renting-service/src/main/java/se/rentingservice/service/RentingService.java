package se.rentingservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.rentingservice.client.CarServiceClient;
import se.rentingservice.client.CustomerServiceClient;
import se.rentingservice.dto.external.CarResponse;
import se.rentingservice.dto.external.CustomerResponse;
import se.rentingservice.dto.request.RentingDetailRequest;
import se.rentingservice.dto.request.RentingTransactionRequest;
import se.rentingservice.dto.response.RentingDetailResponse;
import se.rentingservice.dto.response.RentingTransactionResponse;
import se.rentingservice.exception.BadRequestException;
import se.rentingservice.exception.CarNotAvailableException;
import se.rentingservice.exception.ResourceNotFoundException;
import se.rentingservice.pojo.RentingDetail;
import se.rentingservice.pojo.RentingDetailId;
import se.rentingservice.pojo.RentingTransaction;
import se.rentingservice.repository.RentingDetailRepository;
import se.rentingservice.repository.RentingTransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RentingService {

    private final RentingTransactionRepository rentingTransactionRepository;
    private final RentingDetailRepository rentingDetailRepository;
    private final CarServiceClient carServiceClient;
    private final CustomerServiceClient customerServiceClient;

    public RentingTransactionResponse createRentingTransaction(RentingTransactionRequest request, String userEmail) {
        CustomerResponse customer = customerServiceClient.getCustomerByEmail(userEmail);
        
        if (customer == null || customer.getCustomerId() == null) {
            throw new ResourceNotFoundException("Customer not found with email: " + userEmail);
        }
        
        validateRentingDates(request);
        checkCarAvailability(request);

        BigDecimal totalPrice = calculateTotalPrice(request);

        RentingTransaction transaction = RentingTransaction.builder()
                .rentingDate(LocalDate.now())
                .totalPrice(totalPrice)
                .customerId(customer.getCustomerId())
                .rentingStatus((byte) 1)
                .build();
        
        RentingTransaction savedTransaction = rentingTransactionRepository.save(transaction);
        List<RentingDetail> rentingDetails = new ArrayList<>();
        for (RentingDetailRequest detailRequest : request.getRentingDetails()) {
            BigDecimal price = calculatePrice(detailRequest);
            
            RentingDetail detail = RentingDetail.builder()
                    .id(new RentingDetailId(savedTransaction.getRentingTransactionId(), detailRequest.getCarId()))
                    .startDate(detailRequest.getStartDate())
                    .endDate(detailRequest.getEndDate())
                    .price(price)
                    .rentingTransaction(savedTransaction)
                    .build();
            
            rentingDetails.add(detail);
        }
          List<RentingDetail> savedDetails = rentingDetailRepository.saveAll(rentingDetails);

        updateCarStatusToRented(request);

        RentingTransaction finalTransaction = rentingTransactionRepository.findById(savedTransaction.getRentingTransactionId())
                .orElse(savedTransaction);
        finalTransaction.setRentingDetails(savedDetails);
        
        return convertToResponse(finalTransaction);
    }

    public List<RentingTransactionResponse> getRentingHistory(Integer customerId) {
        List<RentingTransaction> transactions = rentingTransactionRepository
                .findByCustomerIdOrderByRentingDateDesc(customerId);
        
        return transactions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public RentingTransactionResponse getRentingTransactionById(Integer transactionId) {
        RentingTransaction transaction = rentingTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Renting transaction not found with id: " + transactionId));
        
        return convertToResponse(transaction);
    }

    public RentingTransactionResponse updateRentingStatus(Integer transactionId, Byte status) {
        RentingTransaction transaction = rentingTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Renting transaction not found with id: " + transactionId));
        
        transaction.setRentingStatus(status);

        if (status == 2 || status == 3) {
            updateCarStatusToAvailable(transaction);
        }
        
        RentingTransaction updatedTransaction = rentingTransactionRepository.save(transaction);
        return convertToResponse(updatedTransaction);
    }

    public List<RentingTransactionResponse> getRentingByDateRange(LocalDate startDate, LocalDate endDate) {
        List<RentingTransaction> transactions = rentingTransactionRepository
                .findByRentingDateBetween(startDate, endDate);
        
        return transactions.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private void validateRentingDates(RentingTransactionRequest request) {
        for (RentingDetailRequest detail : request.getRentingDetails()) {
            if (detail.getEndDate().isBefore(detail.getStartDate()) || 
                detail.getEndDate().isEqual(detail.getStartDate())) {
                throw new BadRequestException("End date must be after start date for car ID: " + detail.getCarId());
            }
        }
    }

    private void checkCarAvailability(RentingTransactionRequest request) {
        for (RentingDetailRequest detail : request.getRentingDetails()) {
            try {
                CarResponse car = carServiceClient.getCarById(detail.getCarId());
                if (car == null) {
                    throw new ResourceNotFoundException("Car with ID " + detail.getCarId() + " not found");
                }

                if ("Service Unavailable".equals(car.getCarName()) || "Unknown".equals(car.getCarStatus())) {
                    throw new RuntimeException("Car service is currently unavailable");
                }
                
                if (!"Available".equals(car.getCarStatus())) {
                    throw new CarNotAvailableException("Car with ID " + detail.getCarId() + " is not available");
                }
            } catch (Exception e) {
                if (e instanceof CarNotAvailableException || e instanceof ResourceNotFoundException) {
                    throw e;
                }
                throw new ResourceNotFoundException("Car with ID " + detail.getCarId() + " not found");
            }
            
            List<RentingDetail> conflictingRentals = rentingDetailRepository
                    .findByIdCarIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                            detail.getCarId(), detail.getEndDate(), detail.getStartDate());
            
            if (!conflictingRentals.isEmpty()) {
                throw new CarNotAvailableException("Car with ID " + detail.getCarId() + 
                        " is already rented during the requested period");
            }
        }
    }

    private BigDecimal calculateTotalPrice(RentingTransactionRequest request) {
        return request.getRentingDetails().stream()
                .map(this::calculatePrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculatePrice(RentingDetailRequest detail) {
        try {
            CarResponse car = carServiceClient.getCarById(detail.getCarId());
            BigDecimal dailyRate = car.getCarRentingPricePerDay();
            long days = ChronoUnit.DAYS.between(detail.getStartDate(), detail.getEndDate());
            return dailyRate.multiply(BigDecimal.valueOf(days));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Could not calculate price for car ID: " + detail.getCarId());
        }
    }    private void updateCarStatusToRented(RentingTransactionRequest request) {
        for (RentingDetailRequest detail : request.getRentingDetails()) {
            try {
                carServiceClient.updateCarStatus(detail.getCarId(), (byte) 0); // 0 = Rented
                System.out.println("Car status updated: Car " + detail.getCarId() + " status changed to rented");
            } catch (Exception e) {
                System.err.println("Failed to update car status for car " + detail.getCarId() + ": " + e.getMessage());
            }
        }
    }

    private void updateCarStatusToAvailable(RentingTransaction transaction) {
        for (RentingDetail detail : transaction.getRentingDetails()) {
            try {
                carServiceClient.updateCarStatus(detail.getId().getCarId(), (byte) 1); // 1 = Available
                System.out.println("Car status updated: Car " + detail.getId().getCarId() + " status changed to available");
            } catch (Exception e) {
                System.err.println("Failed to update car status for car " + detail.getId().getCarId() + ": " + e.getMessage());
            }
        }
    }

    private RentingTransactionResponse convertToResponse(RentingTransaction transaction) {
        List<RentingDetailResponse> detailResponses = transaction.getRentingDetails().stream()
                .map(this::convertDetailToResponse)
                .collect(Collectors.toList());

        return RentingTransactionResponse.builder()
                .rentingTransactionId(transaction.getRentingTransactionId())
                .rentingDate(transaction.getRentingDate())
                .totalPrice(transaction.getTotalPrice())
                .customerId(transaction.getCustomerId())
                .rentingStatus(getRentingStatusString(transaction.getRentingStatus()))
                .rentingDetails(detailResponses)
                .build();
    }

    private RentingDetailResponse convertDetailToResponse(RentingDetail detail) {
        String carName;
        try {
            CarResponse car = carServiceClient.getCarById(detail.getId().getCarId());
            carName = car.getCarName();
        } catch (Exception e) {
            carName = "Unknown Car";
        }
        
        return RentingDetailResponse.builder()
                .carId(detail.getId().getCarId())
                .carName(carName)
                .startDate(detail.getStartDate())
                .endDate(detail.getEndDate())
                .price(detail.getPrice())
                .build();
    }

    private String getRentingStatusString(Byte status) {
        return switch (status) {
            case 1 -> "Active";
            case 2 -> "Completed";
            case 3 -> "Cancelled";
            default -> "Unknown";
        };
    }
}
