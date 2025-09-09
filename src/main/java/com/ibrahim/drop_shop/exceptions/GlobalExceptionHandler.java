package com.ibrahim.drop_shop.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundExceptions(NotFoundException e) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistsExceptions(AlreadyExistsException e) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestExceptions(BadRequestException e) {
        ExceptionResponse response = new ExceptionResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
