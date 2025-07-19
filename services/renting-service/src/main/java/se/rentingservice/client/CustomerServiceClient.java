package se.rentingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.rentingservice.client.fallback.CustomerServiceFallback;
import se.rentingservice.dto.external.CustomerResponse;

@FeignClient(name = "customer-service", fallback = CustomerServiceFallback.class)
public interface CustomerServiceClient {
    
    @GetMapping("/api/v1/internal/customer/by-email")
    CustomerResponse getCustomerByEmail(@RequestParam String email);
}
