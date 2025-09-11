package com.ibrahim.drop_shop.services.auth;

import com.ibrahim.drop_shop.services.auth.DTO.LoginUserDto;
import com.ibrahim.drop_shop.services.user.DTO.CreateUserDto;

public interface IAuthService {
    String register(CreateUserDto dto);
    String login(LoginUserDto dto);
    String forgotPassword();
}
