package com.ibrahim.drop_shop.security;


import com.ibrahim.drop_shop.exceptions.NotFoundException;
import com.ibrahim.drop_shop.models.User;
import com.ibrahim.drop_shop.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws NotFoundException {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found."));
        return new CustomUserDetails(user);
    }
}
