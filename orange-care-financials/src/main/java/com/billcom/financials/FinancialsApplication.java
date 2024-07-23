package com.billcom.financials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;

@SpringBootApplication
@EnableDiscoveryClient
public class FinancialsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialsApplication.class);
    }

}
