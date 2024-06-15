package com.billcom.payment.config;

import com.billcom.payment.commons.dtos.postgres.UserDto;
import com.billcom.payment.commons.mappers.postgres.UserMapper;
import com.billcom.payment.commons.repositories.postgres.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final UserRepository repository;
    private final UserMapper userMapper;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findUserByLogin(username)
                .map(userMapper::toDto)
                .map(userDto -> {
                    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    return UserDto.builder()
                            .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                            .login(userDto.getLogin())
                            .enabled(userDto.getEnabled())
                            .build();

                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found : "+username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

}
