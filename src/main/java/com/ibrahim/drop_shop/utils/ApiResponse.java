package com.ibrahim.drop_shop.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private Object data;

    static public ResponseEntity<ApiResponse> sendResponse(String message, HttpStatus status, Object data) {
        return ResponseEntity.status(status).body(new ApiResponse(message, data));
    }
}
