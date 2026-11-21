package com.club.closed_club.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.swing.text.html.parser.Entity;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler (EntityNotFoundException.class)
    ResponseEntity<Void> handleEntityNotFound (EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
