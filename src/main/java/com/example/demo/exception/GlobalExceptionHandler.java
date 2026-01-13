package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.dto.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 🔐 AUTHORIZATION FAILURE (403)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException ex) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.failure(
                        "ACCESS_DENIED",
                        "You do not have permission to perform this action"
                ));
    }

    // 🔍 RESOURCE NOT FOUND (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(ResourceNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(
                        "NOT_FOUND",
                        ex.getMessage()
                ));
    }

    // 🧪 VALIDATION ERRORS (400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                           .getFieldErrors()
                           .get(0)
                           .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(
                        "VALIDATION_ERROR",
                        message
                ));
    }

    // 💥 FALLBACK (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure(
                        "INTERNAL_ERROR", ex.getMessage()
                       /// "Something went wrong. Please try again."
                ));
    }
}
