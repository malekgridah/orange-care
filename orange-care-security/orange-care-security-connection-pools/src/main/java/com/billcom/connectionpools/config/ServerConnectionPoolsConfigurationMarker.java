package com.billcom.connectionpools.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ServerConnectionPoolsConfigurationMarker {
    public ServerConnectionPoolsConfigurationMarker() {
    }

    @Bean
    public Marker enableConfigServerMarker() {
        return new Marker();
    }

    class Marker {
        Marker() {
        }
    }
}
