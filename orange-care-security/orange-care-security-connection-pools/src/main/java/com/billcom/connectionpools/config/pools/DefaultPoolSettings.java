package com.billcom.connectionpools.config.pools;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties("default.pool")
public class DefaultPoolSettings {
    private Integer connectionTimeout;
    private Integer maxNumConnections;
    private Integer minNumConnections;
    private Integer noOfConnectionAttempts;
    private Integer connectionAttemptInterval;

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public Integer getMaxNumConnections() {
        return maxNumConnections;
    }

    public Integer getMinNumConnections() {
        return minNumConnections;
    }

    public Integer getNoOfConnectionAttempts() {
        return noOfConnectionAttempts;
    }

    public Integer getConnectionAttemptInterval() {
        return connectionAttemptInterval;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setMaxNumConnections(Integer maxNumConnections) {
        this.maxNumConnections = maxNumConnections;
    }

    public void setMinNumConnections(Integer minNumConnections) {
        this.minNumConnections = minNumConnections;
    }

    public void setNoOfConnectionAttempts(Integer noOfConnectionAttempts) {
        this.noOfConnectionAttempts = noOfConnectionAttempts;
    }

    public void setConnectionAttemptInterval(Integer connectionAttemptInterval) {
        this.connectionAttemptInterval = connectionAttemptInterval;
    }
}
