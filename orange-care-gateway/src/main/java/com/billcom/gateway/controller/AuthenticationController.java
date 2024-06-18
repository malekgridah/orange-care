package com.billcom.gateway.controller;

import com.billcom.gateway.domains.AuthenticationResponse;
import com.billcom.gateway.domains.BscsUser;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class AuthenticationController {

    private final WebClient.Builder webClient;

    public AuthenticationController(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    @PostMapping("/authenticate")
    public Mono<ResponseEntity<AuthenticationResponse>> authenticate(@RequestBody BscsUser user) {
        String baseUrl = "http://localhost:8080";
        return webClient.baseUrl(baseUrl)
                .build()
                .post()
                .uri("auth/authenticate")
                .body(Mono.just(user), BscsUser.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AuthenticationResponse.class)
                .flatMap(data -> Mono.just(ResponseEntity.ok()
                        .body(data)))
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest()
                        .build()));
    }

}
