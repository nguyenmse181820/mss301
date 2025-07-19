package se.rentingservice.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.stereotype.Component;
import se.rentingservice.exception.ResourceNotFoundException;

@Component
public class CarServiceErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 404 -> new ResourceNotFoundException("Car not found");
            case 400 -> new RuntimeException("Bad request to car service");
            case 500 -> new RuntimeException("Car service internal error");
            default -> new RuntimeException("Unknown error occurred");
        };
    }
}
