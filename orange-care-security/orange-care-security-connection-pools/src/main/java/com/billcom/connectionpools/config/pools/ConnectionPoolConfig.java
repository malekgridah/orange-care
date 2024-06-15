package com.billcom.connectionpools.config.pools;

import com.billcom.connectionpools.beans.ConnectionPoolName;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties("connection")
public class ConnectionPoolConfig {

    private List<ConnectionPoolName> pools = new ArrayList<>();

    public List<ConnectionPoolName> getPools() {
        return pools;
    }

    public void setPools(List<ConnectionPoolName> pool) {
        this.pools = pool;
    }
}
