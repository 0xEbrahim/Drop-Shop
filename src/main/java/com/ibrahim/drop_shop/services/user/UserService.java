package com.ibrahim.drop_shop.services.user;

import com.ibrahim.drop_shop.exceptions.AlreadyExistsException;
import com.ibrahim.drop_shop.exceptions.NotFoundException;
import com.ibrahim.drop_shop.models.User;
import com.ibrahim.drop_shop.repositories.UserRepository;
import com.ibrahim.drop_shop.services.user.DTO.CreateUserDto;
import com.ibrahim.drop_shop.services.user.DTO.UpdateUserDto;
import com.ibrahim.drop_shop.services.user.DTO.UserResponseDto;
import com.ibrahim.drop_shop.utils.ResponseTransformer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final ResponseTransformer responseTransformer;
    private final BCryptPasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ResponseTransformer responseTransformer){
        this.userRepository = userRepository;
        this.responseTransformer = responseTransformer;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return responseTransformer.transformToDto(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto createUser(CreateUserDto dto) {
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new AlreadyExistsException("Email already exists");
        }

        User user = User
                .builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();
        user= userRepository.save(user);
        return responseTransformer.transformToDto(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto updateUser(UpdateUserDto dto, Long id) {
        User user = userRepository
                .findById(id)
                .map(usr -> {
            if(dto.getFirstName() != null){
                usr.setFirstName(dto.getFirstName());
            }
            if(dto.getLastName() != null){
                usr.setLastName(dto.getLastName());
            }
            usr = userRepository.save(usr);
            return usr;
        })
                .orElseThrow(() -> new NotFoundException("User not found"));
        return responseTransformer.transformToDto(user, UserResponseDto.class);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(u -> responseTransformer.transformToDto(u, UserResponseDto.class))
                .toList();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(userRepository :: delete, () -> {
            throw new NotFoundException("User not found");
        });
    }
}
