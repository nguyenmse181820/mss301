package se.rentingservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private Integer statusCode;
    private String message;
    private T data;
    private Object error;
}
