package com.example.nbcnewsfeed.friend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
        Map<String, Object> errors = new HashMap<>();

        errors.put("status", ex.getStatusCode().value());
        errors.put("message", ex.getMessage());
        errors.put("path", request.getRequestURI());

        return ResponseEntity.status(ex.getStatusCode()).body(errors);
    }


}