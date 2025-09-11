package com.ibrahim.drop_shop.controllers;


import com.ibrahim.drop_shop.services.auth.AuthService;
import com.ibrahim.drop_shop.services.user.DTO.CreateUserDto;
import com.ibrahim.drop_shop.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("register")
    public ResponseEntity<ApiResponse> register(@RequestBody CreateUserDto createUserDto) {
        String created = authService.register(createUserDto);
        return ApiResponse.sendResponse(created, HttpStatus.CREATED, null);
    }
}
