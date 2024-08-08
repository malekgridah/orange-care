package com.billcom.bscs.clients.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@RefreshScope
@ConfigurationProperties("endpoint.service")
public class WebServiceConfig {

    private String wsiUrl;
    private String wsiUser;
    private String wsiPass;

    private String contractHandlingUrl;
    private String contractHandlingUser;
    private String contractHandlingPass;

    private String customerHandlingUrl;
    private String customerHandlingUser;
    private String customerHandlingPass;

}
