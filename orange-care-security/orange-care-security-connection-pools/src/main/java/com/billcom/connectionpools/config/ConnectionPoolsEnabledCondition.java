package com.billcom.connectionpools.config;

import com.billcom.connectionpools.config.properties.ServerConnectionSettings;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ConnectionPoolsEnabledCondition extends SpringBootCondition {

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ServerConnectionSettings settingsProperties = getSettingsProperties(context);

        if (!settingsProperties.getConfig().isEnabled()) {
            return ConditionOutcome
                    .noMatch("Spring Boot Server Connection Pools is disabled," +
                            " because 'server.connection.settings.config.enabled' is false.");
        }
        return ConditionOutcome.match();
    }

    private ServerConnectionSettings getSettingsProperties(ConditionContext context) {
        ServerConnectionSettings settingsProperties = new ServerConnectionSettings();
        Binder.get(context.getEnvironment()).bind("server.connection.settings", Bindable.ofInstance(settingsProperties));
        return settingsProperties;
    }
}
