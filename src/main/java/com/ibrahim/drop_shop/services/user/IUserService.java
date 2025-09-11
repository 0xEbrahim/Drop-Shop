package com.ibrahim.drop_shop.services.user;

import com.ibrahim.drop_shop.services.user.DTO.CreateUserDto;
import com.ibrahim.drop_shop.services.user.DTO.UpdateUserDto;
import com.ibrahim.drop_shop.services.user.DTO.UserResponseDto;

import java.util.List;

public interface IUserService {
    UserResponseDto getUserById(Long id);
    UserResponseDto createUser(CreateUserDto dto);
    UserResponseDto updateUser(UpdateUserDto dto, Long id);
    List<UserResponseDto> getAllUsers();
    void deleteUser(Long id);

}
