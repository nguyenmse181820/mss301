package se.customerservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.customerservice.dto.request.CustomerRequest;
import se.customerservice.dto.response.ApiResponse;
import se.customerservice.dto.response.CustomerResponse;
import se.customerservice.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/customers")
@RequiredArgsConstructor
public class AdminCustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomers(
            @RequestHeader("X-User-Email") String userEmail) {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(
                ApiResponse.<List<CustomerResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Customers retrieved successfully")
                        .data(customers)
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomer(
            @PathVariable Integer id,
            @RequestHeader("X-User-Email") String userEmail) {
        CustomerResponse customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Customer retrieved successfully")
                        .data(customer)
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(
            @Valid @RequestBody CustomerRequest request,
            @RequestHeader("X-User-Email") String userEmail) {
        CustomerResponse customer = customerService.createCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<CustomerResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Customer created successfully")
                        .data(customer)
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable Integer id,
            @Valid @RequestBody CustomerRequest request,
            @RequestHeader("X-User-Email") String userEmail) {
        CustomerResponse customer = customerService.updateCustomerByAdmin(id, request);
        return ResponseEntity.ok(
                ApiResponse.<CustomerResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Customer updated successfully")
                        .data(customer)
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @PathVariable Integer id,
            @RequestHeader("X-User-Email") String userEmail) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Customer deleted successfully")
                        .build()
        );
    }
}