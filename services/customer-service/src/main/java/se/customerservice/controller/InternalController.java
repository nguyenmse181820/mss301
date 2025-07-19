package se.customerservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.customerservice.dto.response.CustomerResponse;
import se.customerservice.service.CustomerService;

@RestController
@RequestMapping("/api/v1/internal")
@RequiredArgsConstructor
public class InternalController {

    private final CustomerService customerService;

    @GetMapping("/customer/by-email")
    public ResponseEntity<CustomerResponse> getCustomerByEmail(@RequestParam String email) {
        CustomerResponse customer = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(customer);
    }
}
