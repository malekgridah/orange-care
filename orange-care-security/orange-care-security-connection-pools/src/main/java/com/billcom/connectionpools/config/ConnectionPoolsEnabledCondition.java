package com.billcom.connectionpools.config;

import com.billcom.connectionpools.config.pools.ConnectionPoolsProperties;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ConnectionPoolsEnabledCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConnectionPoolsProperties poolsProperties = getClientProperties(context);

        if (!poolsProperties.getPools().isEnabled()) {
            return ConditionOutcome
                    .noMatch("Spring Boot Connection Pools is disabled, because 'billcom.connection.enabled' is false.");
        }
        return ConditionOutcome.match();
    }

    private ConnectionPoolsProperties getClientProperties(ConditionContext context) {
        ConnectionPoolsProperties poolsProperties = new ConnectionPoolsProperties();
        Binder.get(context.getEnvironment()).bind("billcom.connection", Bindable.ofInstance(poolsProperties));
        return poolsProperties;
    }
}
