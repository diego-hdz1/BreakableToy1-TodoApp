package com.encora.ToDosBackend;

import com.encora.ToDosBackend.service.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 * Catches and processes exceptions thrown by controller methods,
 * returning appropriate HTTP responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation-related exceptions and returns a 400 Bad Request.
     *
     * @param exception the thrown ValidationException
     * @return ResponseEntity with error message and HTTP 400 status
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    /**
     * Handles uncaught exceptions and returns a 500 Internal Server Error.
     *
     * @param exception the thrown Exception
     * @return ResponseEntity with generic error message and HTTP 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }
}
