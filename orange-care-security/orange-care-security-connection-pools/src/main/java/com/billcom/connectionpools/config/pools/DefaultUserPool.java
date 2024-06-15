package com.billcom.connectionpools.config.pools;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("default.connection")
public class DefaultUserPool {
    private String defaultConnectionUser;

    public String getDefaultConnectionUser() {
        return this.defaultConnectionUser;
    }

    public void setDefaultConnectionUser(String defaultConnectionUser) {
        this.defaultConnectionUser = defaultConnectionUser;
    }
}
