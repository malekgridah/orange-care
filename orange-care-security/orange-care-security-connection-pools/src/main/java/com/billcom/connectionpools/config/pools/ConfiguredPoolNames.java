package com.billcom.connectionpools.config.pools;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("server.pools.config")
public class ConfiguredPoolNames {
    private String connectionPoolNames;

    public String getConnectionPoolNames() {
        return this.connectionPoolNames;
    }

    public void setConnectionPoolNames(String connectionPoolName) {
        this.connectionPoolNames = connectionPoolName;
    }
}
