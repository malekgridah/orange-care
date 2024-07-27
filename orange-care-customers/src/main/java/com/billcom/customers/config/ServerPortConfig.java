package com.billcom.customers.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerPortConfig {

    @Bean
    public ApplicationListener<WebServerInitializedEvent> webServerInitializedListener() {
        return event -> {
            int actualPort = event.getWebServer().getPort();
            System.out.println(actualPort);
            System.setProperty("actual.server.port", String.valueOf(actualPort));
        };
    }
}
