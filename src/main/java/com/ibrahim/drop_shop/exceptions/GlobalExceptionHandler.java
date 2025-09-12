package com.ibrahim.drop_shop.exceptions;


import io.jsonwebtoken.security.SignatureException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.io.IOException;
import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ExceptionResponse> handleSignatureExceptions(SignatureException e){
        ExceptionResponse response = new ExceptionResponse(HttpStatus.UNAUTHORIZED, "Invalid or expired JsonWebToken.");
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthenticationExceptions(AuthenticationException e){
        ExceptionResponse response = new ExceptionResponse( HttpStatus.FORBIDDEN, e.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUnAuthorizedExceptions(UnAuthorizedException e) {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundExceptions(NotFoundException e) {
        ExceptionResponse response = new ExceptionResponse( HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistsExceptions(AlreadyExistsException e) {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestExceptions(BadRequestException e) {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ExceptionResponse> handleIOExceptions(IOException e){
        ExceptionResponse response = new ExceptionResponse( HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ExceptionResponse> handleDataExceptions(DataAccessException e) {
        Throwable cause = e.getRootCause();
        if (cause instanceof SQLException sqlException) {
            String sqlState = sqlException.getSQLState();
            int errorCode = sqlException.getErrorCode();
            if ("23505".equals(sqlState)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new ExceptionResponse(HttpStatus.CONFLICT,"Duplicate entry: " + sqlException.getMessage()));
            } else if ("08001".equals(sqlState) || "08006".equals(sqlState)) { // Connection failed
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new ExceptionResponse(HttpStatus.SERVICE_UNAVAILABLE,"Database connection failed"));
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Database error occurred: " + e.getMessage()));
    }
}
