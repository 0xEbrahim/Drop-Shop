package com.ibrahim.drop_shop.controllers;


import com.ibrahim.drop_shop.services.user.DTO.CreateUserDto;
import com.ibrahim.drop_shop.services.user.DTO.UpdateUserDto;
import com.ibrahim.drop_shop.services.user.DTO.UserResponseDto;
import com.ibrahim.drop_shop.services.user.IUserService;
import com.ibrahim.drop_shop.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserDto dto) {
        UserResponseDto user = userService.createUser(dto);
        return ApiResponse.sendResponse("Created", HttpStatus.CREATED, user);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ApiResponse.sendResponse("Found", HttpStatus.OK, users);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable("id") Long id) {
        UserResponseDto user = userService.getUserById(id);
        return ApiResponse.sendResponse("Found", HttpStatus.OK, user);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ApiResponse.sendResponse("User has been deleted", HttpStatus.OK, null);
    }

    @PatchMapping("{id}")
    public ResponseEntity<ApiResponse> updateUserById(@RequestBody UpdateUserDto dto, @PathVariable("id") Long id) {
        UserResponseDto user = userService.updateUser(dto, id);
        return ApiResponse.sendResponse("User has been updated", HttpStatus.OK, user);
    }

}
