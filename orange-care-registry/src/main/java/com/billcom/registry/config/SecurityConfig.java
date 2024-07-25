package com.billcom.registry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

//    @Bean
//    @Profile("secure")
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/eureka/","/eureka/**", "/actuator/**").permitAll()
//                        .anyRequest().authenticated())
//                .csrf((csrf) -> csrf.ignoringRequestMatchers("/eureka/","/eureka/**", "/actuator/**"))
//                .oauth2Login(Customizer.withDefaults());
//        return http.build();
//    }

    @Bean
    @Profile("secure")
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").authenticated()
                        .anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    @Profile("insecure")
    public SecurityFilterChain securityWebFilterChainPermitAll(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((authorizeExchange) -> authorizeExchange.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
