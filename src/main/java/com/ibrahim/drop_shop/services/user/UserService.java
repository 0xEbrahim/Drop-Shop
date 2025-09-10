package com.ibrahim.drop_shop.services.user;

import com.ibrahim.drop_shop.services.user.DTO.CreateUserDto;
import com.ibrahim.drop_shop.services.user.DTO.UpdateUserDto;
import com.ibrahim.drop_shop.services.user.DTO.UserResponseDto;

import java.util.List;

public class UserService implements IUserService{
    @Override
    public UserResponseDto getUserById(Long id) {
        return null;
    }

    @Override
    public UserResponseDto createUser(CreateUserDto dto) {
        return null;
    }

    @Override
    public UserResponseDto updateUser(UpdateUserDto dto, Long id) {
        return null;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return List.of();
    }

    @Override
    public void deleteUser(Long id) {

    }
}
