package com.billcom.bscs.clients.config;


import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
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


    public void setWsiUrl(String wsiUrl) {
        this.wsiUrl = wsiUrl;
    }

    public void setWsiUser(String wsiUser) {
        this.wsiUser = wsiUser;
    }

    public void setWsiPass(String wsiPass) {
        this.wsiPass = wsiPass;
    }



    public void setContractHandlingUrl(String contractHandlingUrl) {
        this.contractHandlingUrl = contractHandlingUrl;
    }

    public void setContractHandlingUser(String contractHandlingUser) {
        this.contractHandlingUser = contractHandlingUser;
    }

    public void setContractHandlingPass(String contractHandlingPass) {
        this.contractHandlingPass = contractHandlingPass;
    }



    public void setCustomerHandlingUrl(String customerHandlingUrl) {
        this.customerHandlingUrl = customerHandlingUrl;
    }

    public void setCustomerHandlingUser(String customerHandlingUser) {
        this.customerHandlingUser = customerHandlingUser;
    }

    public void setCustomerHandlingPass(String customerHandlingPass) {
        this.customerHandlingPass = customerHandlingPass;
    }
}
