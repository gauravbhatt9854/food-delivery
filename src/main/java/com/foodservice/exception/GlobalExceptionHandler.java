package com.foodservice.exception;
import com.foodservice.entity.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import java.util.stream.Collectors;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(OrderInvalidRequestException.class)
    public ResponseEntity<ApiResponseDTO> handleOrderInvalidRequestException(OrderInvalidRequestException orderInvalidRequestException) {

        String message = HttpStatus.NOT_FOUND.toString()+", "+orderInvalidRequestException.getMessage();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO(HttpStatus.NOT_FOUND.value(), message, null));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponseDTO> handleTypeMismatch (MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {

        String message = HttpStatus.NOT_FOUND.toString()+", "+methodArgumentTypeMismatchException.getMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(), message, null));

    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ApiResponseDTO> handleInvalidDateRangeException(InvalidDateRangeException ex) {
        log.warn("Invalid date range: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null));
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ApiResponseDTO> handleRestaurantNotFoundException(RestaurantNotFoundException ex){
        log.warn("Restaurant not found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO(HttpStatus.NOT_FOUND.value(),ex.getMessage(),null));
    }

    @ExceptionHandler(MenuItemNotFoundException.class)
    public ResponseEntity<ApiResponseDTO> handleMenuItemNotFoundException(MenuItemNotFoundException ex) {
        log.warn("Menu item not found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null));
    }

    @ExceptionHandler(RestaurantInvalidRequestException.class)
    public ResponseEntity<ApiResponseDTO> handleRestaurantInvalidRequestException(RestaurantInvalidRequestException ex) {
        log.warn("Invalid restaurant request: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {

        String message = HttpStatus.NOT_FOUND.toString()+", "+ex.getMessage();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO(HttpStatus.NOT_FOUND.value(), message, null));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.warn("Validation failed: {}", message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(), message, null));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponseDTO> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        String message = "Required request parameter '" + ex.getParameterName() + "' is not present";
        log.warn("Missing request parameter: {}", message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(), message, null));
    }


    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ApiResponseDTO> handleConstraintViolationException(jakarta.validation.ConstraintViolationException ex) {

        String message = HttpStatus.BAD_REQUEST.toString() + ", " + ex.getMessage();

        log.warn("Validation failed: {}", message);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(), message, null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO> handleGenericException(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred. Please try again later.", null));
    }

}
