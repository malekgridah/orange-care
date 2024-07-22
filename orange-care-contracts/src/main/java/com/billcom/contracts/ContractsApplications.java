package com.billcom.contracts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ContractsApplications {

    public static void main(String[] args) {
        SpringApplication.run(ContractsApplications.class, args);
    }
}
