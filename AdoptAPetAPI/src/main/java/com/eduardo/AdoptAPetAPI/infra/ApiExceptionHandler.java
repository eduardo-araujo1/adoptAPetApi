package com.eduardo.AdoptAPetAPI.infra;

import com.eduardo.AdoptAPetAPI.exceptions.EmailNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<ApiErrorMessage> emailNotFound(EmailNotFoundException exception) {
        ApiErrorMessage threatResponse = new ApiErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }
}
