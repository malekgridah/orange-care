package com.billcom.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrangeCareUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrangeCareUiApplication.class, args);
    }
}
