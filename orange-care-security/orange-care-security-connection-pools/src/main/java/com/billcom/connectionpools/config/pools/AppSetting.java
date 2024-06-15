package com.billcom.connectionpools.config.pools;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app")
public class AppSetting {

    private String cacheValidityHours;

    private String automaticRetryNumber;

    private String soiName;

    private String soiVersion;

    private String serverName;


    public String getCacheValidityHours() {
        return cacheValidityHours;
    }

    public void setCacheValidityHours(String cacheValidityHours) {
        this.cacheValidityHours = cacheValidityHours;
    }

    public String getAutomaticRetryNumber() {
        return automaticRetryNumber;
    }

    public void setAutomaticRetryNumber(String automaticRetryNumber) {
        this.automaticRetryNumber = automaticRetryNumber;
    }

    public String getSoiName() {
        return soiName;
    }

    public void setSoiName(String soiName) {
        this.soiName = soiName;
    }

    public String getSoiVersion() {
        return soiVersion;
    }

    public void setSoiVersion(String soiVersion) {
        this.soiVersion = soiVersion;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
