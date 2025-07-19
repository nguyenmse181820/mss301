package se.rentingservice.client.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.rentingservice.client.CustomerServiceClient;
import se.rentingservice.dto.external.CustomerResponse;

@Component
@Slf4j
public class CustomerServiceFallback implements CustomerServiceClient {
    
    @Override
    public CustomerResponse getCustomerByEmail(String email) {
        log.error("Customer service is unavailable, cannot resolve email: {}", email);
        throw new RuntimeException("Customer service is currently unavailable");
    }
}
