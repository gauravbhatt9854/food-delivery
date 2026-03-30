package com.foodservice.exception;
import com.foodservice.entity.dto.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(OrderInvalidRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleOrderInvalidRequestException(OrderInvalidRequestException orderInvalidRequestException, WebRequest webRequest) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                LocalDateTime.now(),
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                orderInvalidRequestException.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatch (MethodArgumentTypeMismatchException methodArgumentTypeMismatchException, WebRequest webRequest) {

        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(
                LocalDateTime.now(),
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                methodArgumentTypeMismatchException.getMessage()
        );

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            WebRequest webRequest) {

        log.error(
                "ResourceNotFoundException occurred while processing request. path={}, errorMessage={}, exceptionClass={}",
                webRequest.getDescription(false),
                ex.getMessage(),
                ex.getClass().getSimpleName(),
                ex
        );

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                LocalDateTime.now(),
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );

        log.debug(
                "Returning error response for ResourceNotFoundException. status={}, responseBody={}",
                HttpStatus.NOT_FOUND,
                errorResponse
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}