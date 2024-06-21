package com.billcom.connectionpools.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("server.connection.settings")
public class ServerConnectionSettings {
    private ApplicationProperties application;
    private SoiProperties soi;
    private ServerConnectionProperties config = new ServerConnectionProperties();

    @Data
    public static class ApplicationProperties {
        private Integer retryAttempts;
        private Integer cacheValidityHours;
    }

    @Data
    public static class SoiProperties {
        private String name;
        private Integer version;
        private String serverName;
    }

    @Data
    public static class ServerConnectionProperties {
        private boolean enabled = true;
    }
}
