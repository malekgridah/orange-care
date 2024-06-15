package com.billcom.bscs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.context.WebApplicationContext;

@EnableDiscoveryClient
@SpringBootApplication
public class BcscApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BcscApplication.class);
        app.setApplicationStartup(new BufferingApplicationStartup(2048));
        app.run(args);
    }
}
