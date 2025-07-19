package se.customerservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private Integer statusCode;
    private String message;
    private T data;
    private Object error;
}
