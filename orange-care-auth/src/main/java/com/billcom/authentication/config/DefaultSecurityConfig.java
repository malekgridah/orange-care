package com.billcom.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class DefaultSecurityConfig {

    String[] staticResources  =  {
            "/css/**",
            "/images/**",
            "/fonts/**",
            "/scripts/**",
    };

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(staticResources).permitAll()
                                .anyRequest().authenticated()
                )
                .cors(withDefaults())
                .formLogin(formLogin -> formLogin.loginPage("/login").permitAll());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(new ConnectionPoolsAuthenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
