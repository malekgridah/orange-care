package com.billcom.connectionpools.beans;

public class ConnectionPoolName {
    private String bscsUser;

    private String bscsUserPassword;

    private Integer connectionTimeout;

    private Integer maxNumConnections;

    private Integer minNumConnections;

    private Integer noOfConnectionAttempts;

    private Integer connectionAttemptInterval;

    public String getBscsUser() {
        return this.bscsUser;
    }

    public void setBscsUser(String bscsUser) {
        this.bscsUser = bscsUser;
    }

    public String getBscsUserPassword() {
        return this.bscsUserPassword;
    }

    public void setBscsUserPassword(String bscsUserPassword) {
        this.bscsUserPassword = bscsUserPassword;
    }

    public Integer getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getMaxNumConnections() {
        return this.maxNumConnections;
    }

    public void setMaxNumConnections(Integer maxNumConnections) {
        this.maxNumConnections = maxNumConnections;
    }

    public Integer getMinNumConnections() {
        return this.minNumConnections;
    }

    public void setMinNumConnections(Integer minNumConnections) {
        this.minNumConnections = minNumConnections;
    }

    public Integer getNoOfConnectionAttempts() {
        return this.noOfConnectionAttempts;
    }

    public void setNoOfConnectionAttempts(Integer noOfConnectionAttempts) {
        this.noOfConnectionAttempts = noOfConnectionAttempts;
    }

    public Integer getConnectionAttemptInterval() {
        return this.connectionAttemptInterval;
    }

    public void setConnectionAttemptInterval(Integer connectionAttemptInterval) {
        this.connectionAttemptInterval = connectionAttemptInterval;
    }
}
