package com.billcom.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
public class GatewayDiscoveryConfiguration {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/bscs/**")
                        .filters(fm -> fm.stripPrefix(1))
                        .uri("lb://orange-care-bscs")
                )
                .route(p -> p
                        .path("/**")
                        .uri("lb://orange-care-ui")
                )
                .route(p -> p
                        .path("/swagger-ui/**")
                        .uri("http://localhost:8080")
                )
                .route(p -> p
                        .host("*.circuitbreaker.com")
                        .filters(f -> f.circuitBreaker(config -> config
                                .setName("mycmd")
                                .setFallbackUri("forward:/fallback")))
                        .uri("http://httpbin.org:80"))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
        corsConfig.setAllowedMethods(Collections.singletonList("*"));
        corsConfig.setAllowedHeaders(Collections.singletonList("*"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/bscs/**", corsConfig);
        source.registerCorsConfiguration("/**", corsConfig);

        return source;
    }
}
