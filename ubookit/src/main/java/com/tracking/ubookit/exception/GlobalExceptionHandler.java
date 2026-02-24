package com.tracking.ubookit.exception;

import com.tracking.ubookit.dto.CommonResponse;
import com.tracking.ubookit.constant.CommonConstant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Centralized exception handler for the entire application.
 * Catches specific exceptions and returns appropriate HTTP status codes
 * with a consistent error response body.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Handles requests for orders that don't exist in the database. */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<CommonResponse> handleOrderNotFound(OrderNotFoundException ex) {
        CommonResponse response = CommonResponse.builder()
                .code(CommonConstant.CODE_1)
                .message(CommonConstant.ERROR)
                .description(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /** Handles invalid order ID format (e.g. missing "U" prefix, non-numeric suffix). */
    @ExceptionHandler(InvalidOrderIdException.class)
    public ResponseEntity<CommonResponse> handleInvalidOrderId(InvalidOrderIdException ex) {
        CommonResponse response = CommonResponse.builder()
                .code(CommonConstant.CODE_1)
                .message(CommonConstant.ERROR)
                .description(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /** Handles requests to URLs that don't match any controller mapping (404). */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<CommonResponse> handleNoResourceFound(NoResourceFoundException ex) {
        CommonResponse response = CommonResponse.builder()
                .code(CommonConstant.CODE_1)
                .message(CommonConstant.ERROR)
                .description("The requested resource was not found: " + ex.getResourcePath())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /** Fallback handler for any unexpected server-side errors. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleGenericException(Exception ex) {
        CommonResponse response = CommonResponse.builder()
                .code(CommonConstant.CODE_1)
                .message(CommonConstant.ERROR)
                .description("An unexpected error occurred: " + ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
