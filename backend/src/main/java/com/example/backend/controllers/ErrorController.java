package com.example.backend.controllers;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import jakarta.validation.ConstraintViolation;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorController
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getConstraintViolations().stream().collect(Collectors.toMap(
                        ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage)),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
