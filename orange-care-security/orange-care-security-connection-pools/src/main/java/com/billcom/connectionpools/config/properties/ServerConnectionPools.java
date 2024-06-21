package com.billcom.connectionpools.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties("server.connection.pools")
public class ServerConnectionPools {
    private DefaultConnectionSettings Default;
    private List<String> poolNames;
    private List<ConnectionPoolName> poolsList;

    @Data
    public static class DefaultConnectionSettings {
        private String poolName;
        private ConnectionPool poolSettings;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ConnectionPoolName extends ConnectionPool {
        private String bscsUser;
        private String bscsUserPassword;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ConnectionPool {
        private Integer connectionTimeout;
        private Integer maxNumConnections;
        private Integer minNumConnections;
        private Integer noOfConnectionAttempts;
        private Integer connectionAttemptInterval;
    }
}
