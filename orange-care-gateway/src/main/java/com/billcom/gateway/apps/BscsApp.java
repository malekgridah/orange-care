//package com.billcom.gateway.apps;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
//@Component
//@RequiredArgsConstructor
//public class BscsApp {
//    private final WebClient.Builder webClientBuilder;
//
//    public Mono<OwnerDetails> getOwner(final int ownerId) {
//        return webClientBuilder.build().get()
//                .uri("http://customers-service/owners/{ownerId}", ownerId)
//                .retrieve()
//                .bodyToMono(OwnerDetails.class);
//    }
//}
