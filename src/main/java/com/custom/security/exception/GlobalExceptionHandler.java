package com.custom.security.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.function.EntityResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(SecurityExceptionWithStatusCode.class)
  public ResponseEntity<?> noAccess(SecurityExceptionWithStatusCode se, HttpServletRequest request) {
    int statusCode = se.getStatusCode();
    ErrorMessage errorBody = ErrorMessage.builder().code(HttpStatus.UNAUTHORIZED.value())
        .path(request.getServletPath()).status(HttpStatus.UNAUTHORIZED.name()).message(se.getMessage())
        .timeStamp(LocalDateTime.now()).build();
    return ResponseEntity.status(statusCode).body(errorBody);
  }
}
