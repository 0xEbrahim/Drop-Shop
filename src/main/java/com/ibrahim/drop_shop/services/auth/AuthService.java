package com.ibrahim.drop_shop.services.auth;


import com.ibrahim.drop_shop.models.User;
import com.ibrahim.drop_shop.repositories.UserRepository;
import com.ibrahim.drop_shop.services.auth.DTO.LoginUserDto;
import com.ibrahim.drop_shop.services.user.DTO.CreateUserDto;
import com.ibrahim.drop_shop.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String register(CreateUserDto dto) {
        User user = User
                .builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build();
        userRepository.save(user);
        return "Your account has been created.";
    }

    @Override
    public String login(LoginUserDto dto) {
        return "";
    }

    @Override
    public String forgotPassword() {
        return "";
    }
}
