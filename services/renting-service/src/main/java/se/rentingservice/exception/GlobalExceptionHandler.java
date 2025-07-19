package se.rentingservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.rentingservice.dto.response.ApiResponse;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(BAD_REQUEST)
                .body(ApiResponse.builder()
                        .statusCode(BAD_REQUEST.value())
                        .error(errors)
                        .build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequestException(BadRequestException e) {
        return createErrorResponse(BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException e) {
        return createErrorResponse(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(CarNotAvailableException.class)
    public ResponseEntity<ApiResponse<?>> handleCarNotAvailableException(CarNotAvailableException e) {
        return createErrorResponse(CONFLICT, e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return createErrorResponse(INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    }

    private ResponseEntity<ApiResponse<?>> createErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(ApiResponse.builder()
                        .statusCode(status.value())
                        .message(message)
                        .build());
    }
}
