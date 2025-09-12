package com.ibrahim.drop_shop.services.auth;


import com.ibrahim.drop_shop.exceptions.BadRequestException;
import com.ibrahim.drop_shop.models.User;
import com.ibrahim.drop_shop.repositories.UserRepository;
import com.ibrahim.drop_shop.security.CustomUserDetails;
import com.ibrahim.drop_shop.security.CustomUserDetailsService;
import com.ibrahim.drop_shop.security.JwtService;
import com.ibrahim.drop_shop.services.auth.DTO.AuthenticationResponse;
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
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
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
    public AuthenticationResponse login(LoginUserDto dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        String accessToken = "", refreshToken = "";
        if(authentication.isAuthenticated()){
              accessToken = jwtService.generateAccessToken(dto.getEmail());
              refreshToken = jwtService.generateRefreshToken(dto.getEmail());
        }
        return AuthenticationResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public String forgotPassword() {
        return "";
    }

    @Override
    public AuthenticationResponse refresh(String token) {
        if(!jwtService.isRefreshToken(token)){
            throw new BadRequestException("Invalid or expired refresh Token");
        }
        String email = jwtService.extractRefreshEmail(token);
        CustomUserDetails userDetails =
                customUserDetailsService.loadUserByUsername(email);
        final String accessToken = jwtService.generateAccessToken(userDetails.getUsername());
        return AuthenticationResponse
                .builder()
                .accessToken(accessToken)
                .build();
    }
}
