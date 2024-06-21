package com.billcom.connectionpools.config;

import com.billcom.connectionpools.config.properties.ServerConnectionPools;
import com.billcom.connectionpools.config.properties.ServerConnectionSettings;
import com.billcom.connectionpools.initializer.WsInitializer;
import com.billcom.connectionpools.pools.ServerConnectionPoolInitializer;
import com.billcom.connectionpools.pools.ServerConnectionPoolManager;
import com.billcom.connectionpools.pools.ServerConnectionPoolOptions;
import com.billcom.connectionpools.pools.ServerDefaultConnectionProvider;
import com.billcom.connectionpools.utils.SpringContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;


@Configuration(proxyBeanMethods = false)
@Conditional(ConnectionPoolsEnabledCondition.class)
@ConditionalOnBean(ServerConnectionPoolsConfigurationMarker.Marker.class)
@EnableConfigurationProperties({ServerConnectionPools.class, ServerConnectionSettings.class})
@Lazy(false)
public class ConnectionPoolsAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public WsInitializer wsInitializer() {
        return new WsInitializer();
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringContext springContext() {
        return new SpringContext();
    }

    @Bean
    @ConditionalOnMissingBean
    public ServerConnectionPoolInitializer serverConnectionPoolInitializer(ServerDefaultConnectionProvider serverDefaultConnectionProvider,
                                                                           ServerConnectionPoolManager serverConnectionPoolManager) {
        return new ServerConnectionPoolInitializer(serverDefaultConnectionProvider, serverConnectionPoolManager);
    }

    @Bean
    @ConditionalOnMissingBean
    public ServerDefaultConnectionProvider serverDefaultConnectionProvider(ServerConnectionPoolManager serverConnectionPoolManager) {
        return new ServerDefaultConnectionProvider(serverConnectionPoolManager);
    }

    @Bean
    @ConditionalOnMissingBean
    public ServerConnectionPoolManager serverConnectionPoolManager(ServerConnectionPools serverConnectionPools,
                                                                   ServerConnectionPoolOptions serverConnectionPoolOptions) {
        return new ServerConnectionPoolManager(serverConnectionPools, serverConnectionPoolOptions);
    }

    @Bean
    @ConditionalOnMissingBean
    public ServerConnectionPoolOptions serverConnectionPoolOptions(ServerConnectionPools serverConnectionPools,
                                                                   ConnectionPoolServiceConfig connectionPoolServiceConfig) {
        return new ServerConnectionPoolOptions(serverConnectionPools, connectionPoolServiceConfig);
    }

    @Bean
    @ConditionalOnMissingBean
    public ConnectionPoolServiceConfig connectionPoolServiceConfig(ServerConnectionPools serverConnectionPools) {
        return new ConnectionPoolServiceConfig(serverConnectionPools);
    }

}
