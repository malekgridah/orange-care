package com.billcom.connectionpools.config;

import com.billcom.connectionpools.config.pools.ConnectionPoolsProperties;
import com.billcom.connectionpools.initializer.WsInitializer;
import com.billcom.connectionpools.pools.PoolInitializer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;


@Configuration(proxyBeanMethods = false)
@Conditional(ConnectionPoolsEnabledCondition.class)
@ConditionalOnBean(ConnectionPoolsConfigurationMarker.Marker.class)
@EnableConfigurationProperties({ConnectionPoolsProperties.class})
@ConfigurationPropertiesScan
@AutoConfigureAfter(SpringBootServletInitializer.class)
@Lazy(false)
public class ConnectionPoolsAutoConfig {


    @Bean
    @ConditionalOnMissingBean
    public PoolInitializer poolInitializer() {
        return new PoolInitializer();
    }

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
}
