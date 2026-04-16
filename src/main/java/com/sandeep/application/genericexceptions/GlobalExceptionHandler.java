package com.sandeep.application.genericexceptions;

import com.sandeep.application.dto.APIResponseFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404, isko test bhi kr skte he by calling non existing employee id in get by id endpoint
    // or by calling non existing endpoint like /api/employees/9999
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponseFormat<Object>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new APIResponseFormat<>(404, ex.getMessage(), null,ex.getMessage()));
    }

    // 500 (generic)
    // isko test krne ke liye employee controller me get all employees wale endpoint me
    //  ek line add kr do jisme koi null pointer exception ya koi bhi exception throw ho jaye
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponseFormat<Object>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                // .body(new APIResponseFormat<>(500, "Something went wrong", null, "Internal server error"));
                .body(new APIResponseFormat<>(500,ex.getMessage(), null, ex.getClass().getSimpleName()));
    }

    // External API failure
    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<APIResponseFormat<Object>> handleExternal(ExternalServiceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(new APIResponseFormat<>(502, ex.getMessage(), null, ex.getMessage()));
    }
}