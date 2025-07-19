package se.rentingservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.rentingservice.dto.request.RentingTransactionRequest;
import se.rentingservice.dto.response.ApiResponse;
import se.rentingservice.dto.response.RentingTransactionResponse;
import se.rentingservice.service.RentingService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rentals")
@RequiredArgsConstructor
public class RentingController {

    private final RentingService rentingService;

    @PostMapping
    public ResponseEntity<ApiResponse<RentingTransactionResponse>> createRentingTransaction(
            @Valid @RequestBody RentingTransactionRequest request,
            @RequestHeader("X-User-Email") String userEmail) {
        
        RentingTransactionResponse response = rentingService.createRentingTransaction(request, userEmail);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<RentingTransactionResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Rental transaction created successfully")
                        .data(response)
                        .build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<RentingTransactionResponse>>> getRentingHistory(
            @PathVariable Integer customerId,
            @RequestHeader("X-User-Email") String userEmail) {
        
        List<RentingTransactionResponse> history = rentingService.getRentingHistory(customerId);
        
        return ResponseEntity.ok(
                ApiResponse.<List<RentingTransactionResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Rental history retrieved successfully")
                        .data(history)
                        .build());
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<RentingTransactionResponse>> getRentingTransaction(
            @PathVariable Integer transactionId,
            @RequestHeader("X-User-Email") String userEmail) {
        
        RentingTransactionResponse response = rentingService.getRentingTransactionById(transactionId);
        
        return ResponseEntity.ok(
                ApiResponse.<RentingTransactionResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Rental transaction retrieved successfully")
                        .data(response)
                        .build());
    }

    @PutMapping("/{transactionId}/status")
    public ResponseEntity<ApiResponse<RentingTransactionResponse>> updateRentingStatus(
            @PathVariable Integer transactionId,
            @RequestParam Byte status,
            @RequestHeader("X-User-Email") String userEmail) {
        
        RentingTransactionResponse response = rentingService.updateRentingStatus(transactionId, status);
        
        return ResponseEntity.ok(
                ApiResponse.<RentingTransactionResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Rental status updated successfully")
                        .data(response)
                        .build());
    }

    @GetMapping("/reports")
    public ResponseEntity<ApiResponse<List<RentingTransactionResponse>>> getRentingReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestHeader("X-User-Email") String userEmail) {
        
        List<RentingTransactionResponse> rentals = rentingService.getRentingByDateRange(startDate, endDate);
        
        return ResponseEntity.ok(
                ApiResponse.<List<RentingTransactionResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Rental report retrieved successfully")
                        .data(rentals)
                        .build());
    }
}
