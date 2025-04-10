package com.jbrkan.tiszadatak.advice;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> handleResourceAccessException(ResourceAccessException ex) {
        ErrorResponse errorResponse = new ErrorResponse(List.of(ex.getMessage()));
        return new ResponseEntity<>(errorResponse, SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> errorMessages = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ErrorResponse errorResponse = new ErrorResponse(errorMessages);

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errorMessages = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .toList();

        ErrorResponse errorResponse = new ErrorResponse(errorMessages);

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException() {

        ErrorResponse errorResponse = new ErrorResponse(List.of("Data access error"));

        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralExceptions(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(List.of(ex.getMessage()));

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }
}

