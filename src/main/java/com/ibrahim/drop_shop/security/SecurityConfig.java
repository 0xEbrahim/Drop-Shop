package com.ibrahim.drop_shop.security;

import com.ibrahim.drop_shop.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

public class SecurityConfig {
    @Value("${password.rounds:10}")
    private int rounds;
    @Value("${api.prefix}")
    private String prefix;
    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public BCryptPasswordEncoder createPasswordEncoder() {
        return new BCryptPasswordEncoder(rounds);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz ->
                        authz.requestMatchers(prefix + "/auth/**").permitAll()
                                .requestMatchers(HttpMethod.POST, prefix+"/users/**").hasRole(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, prefix + "/products").hasRole(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, prefix + "/products/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name())
                                .requestMatchers(HttpMethod.DELETE, prefix + "/products/**").hasRole(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.PATCH, prefix + "/products/**").hasRole(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, prefix + "/orders").hasRole(UserRole.USER.name())
                                .requestMatchers(HttpMethod.GET, prefix + "/orders/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name())
                                .requestMatchers(prefix + "/images/**").hasRole(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.GET, prefix + "/categories/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name())
                                .requestMatchers(HttpMethod.POST, prefix + "/categories").hasRole(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE, prefix + "/categories/**").hasRole(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.PATCH, prefix + "/categories/**").hasRole(UserRole.ADMIN.name())
                                .requestMatchers(prefix + "/cartItems").hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name())
                                .requestMatchers(HttpMethod.GET, prefix + "/carts/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name())
                                .requestMatchers(HttpMethod.DELETE, prefix + "/carts").hasAnyRole(UserRole.ADMIN.name(), UserRole.USER.name())
                ).sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider =
                new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(createPasswordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration
                    authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
