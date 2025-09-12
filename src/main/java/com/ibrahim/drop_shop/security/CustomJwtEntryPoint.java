package com.ibrahim.drop_shop.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibrahim.drop_shop.exceptions.ExceptionResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomJwtEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomJwtEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.UNAUTHORIZED, "Invalid or expired JsonWebToken");
        String json = objectMapper.writeValueAsString(exceptionResponse);
        response.getWriter().write(json);
    }
}
