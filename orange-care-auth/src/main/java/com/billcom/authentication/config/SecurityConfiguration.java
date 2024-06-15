package com.billcom.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final BasicAuthFilter basicAuthFilter;

    @Autowired
    public SecurityConfiguration(BasicAuthFilter basicAuthFilter) {
        this.basicAuthFilter = basicAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests( auth -> auth
                        .antMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())
                .authenticationManager(new SampleAuthManager())
                .addFilterAfter(basicAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic();
        return http.build();
    }

}

