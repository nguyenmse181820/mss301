package se.customerservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.customerservice.dto.request.ProfileUpdateRequest;
import se.customerservice.dto.response.ApiResponse;
import se.customerservice.dto.response.CustomerResponse;
import se.customerservice.service.CustomerService;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerProfileController {

    private final CustomerService customerService;

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<CustomerResponse>> getProfile(
            @RequestHeader("X-User-Email") String userEmail) {
        CustomerResponse customer = customerService.getProfileByEmail(userEmail);
        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Profile retrieved successfully")
                        .data(customer)
                        .build()
        );
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateProfile(
            @Valid @RequestBody ProfileUpdateRequest request,
            @RequestHeader("X-User-Email") String userEmail) {
        CustomerResponse customer = customerService.updateProfileByEmail(userEmail, request);
        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Profile updated successfully")
                        .data(customer)
                        .build()
        );
    }
}
