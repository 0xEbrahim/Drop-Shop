package com.ibrahim.drop_shop.services.auth;


import com.ibrahim.drop_shop.exceptions.BadRequestException;
import com.ibrahim.drop_shop.models.User;
import com.ibrahim.drop_shop.repositories.UserRepository;
import com.ibrahim.drop_shop.security.JwtService;
import com.ibrahim.drop_shop.services.auth.DTO.LoginUserDto;
import com.ibrahim.drop_shop.services.user.DTO.CreateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateAccessToken(dto.getEmail());
        }
        throw new BadRequestException("Invalid email or password");
    }

    @Override
    public String forgotPassword() {
        return "";
    }
}
