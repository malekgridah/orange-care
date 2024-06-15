package com.billcom.connectionpools.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ConnectionPoolsConfigurationMarker {
    public ConnectionPoolsConfigurationMarker() {
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
