package com.ibrahim.drop_shop.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ExceptionResponse {

    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;

    public ExceptionResponse(LocalDateTime timestamp,HttpStatus status, String message) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }
}
