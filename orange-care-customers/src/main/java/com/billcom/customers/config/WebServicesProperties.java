package com.billcom.customers.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("endpoint.service")
public class WebServicesProperties {
    private EndpointProperties wsi;
    private EndpointProperties customerHandling;

    @Data
    public static class EndpointProperties {
        private String url;
        private String username;
        private String password;
    }
}
