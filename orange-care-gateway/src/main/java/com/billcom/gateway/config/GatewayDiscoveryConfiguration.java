package com.billcom.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

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
                        .path("/contracts/**")
                        .filters(fm -> fm.stripPrefix(1))
                        .uri("lb://orange-care-customers")
                )
                .route(p -> p
                        .path("/bscs/**")
                        .filters(fm -> fm.stripPrefix(1))
                        .uri("lb://orange-care-contracts")
                )
                .route(p -> p
                        .path("/payment/**")
                        .filters(fm -> fm.stripPrefix(1))
                        .uri("lb://orange-care-payment")
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
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOriginPattern("*");
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.setExposedHeaders(Collections.singletonList("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
