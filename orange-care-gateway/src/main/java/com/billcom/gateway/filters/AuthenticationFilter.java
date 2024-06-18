package com.billcom.gateway.filters;

import com.billcom.gateway.exception.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;


import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GatewayFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    private final RouteValidator routeValidator;

    private final WebClient.Builder webClientBuilder;

    private String baseUrl = "http://localhost:8080/bscs";

    public AuthenticationFilter(RouteValidator routeValidator, WebClient.Builder webClientBuilder) {
        this.routeValidator = routeValidator;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Filtering for Token Validation");
        ServerHttpRequest request = exchange.getRequest();

        if (routeValidator.isSecured.test(request)) {
            logger.info("Validating request {}", request.getPath());
            final String token = this.getAuthHeader(request);
            return webClientBuilder.baseUrl(baseUrl)
                    .build()
                    .get()
                    .uri("/security/validate/" + token)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, cr -> Mono.error(new AuthenticationException("Bad token")))
                    .bodyToMono(String.class)
                    .then(chain.filter(exchange));
        }
        return chain.filter(exchange);
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders()
                .getOrEmpty("Authorization")
                .get(0)
                .substring(7);
    }

}
