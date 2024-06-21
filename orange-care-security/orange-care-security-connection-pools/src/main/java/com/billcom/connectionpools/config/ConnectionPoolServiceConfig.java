package com.billcom.connectionpools.config;


import com.billcom.connectionpools.config.properties.ServerConnectionPools;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class ConnectionPoolServiceConfig {

    private final ServerConnectionPools serverConnectionPools;

    @Getter
    private final Map<String, String> defaultOptionsMap = new HashMap<>();
    @Getter
    private final Map<String, Map<String, String>> optionsMap = new HashMap<>();

    public ConnectionPoolServiceConfig(ServerConnectionPools serverConnectionPools) {
        this.serverConnectionPools = serverConnectionPools;
        setOptionMap(null);
        setOptionMapForPools();
    }


    protected void setOptionMap(String pConnectionPoolName) {
        if (pConnectionPoolName == null) {
            // Populate default options map using reflection
            ServerConnectionPools.DefaultConnectionSettings defaultSettings = serverConnectionPools.getDefault();
            populateOptionsMap(defaultOptionsMap, defaultSettings.getPoolSettings());
        } else {
            // Set specific pool options
            List<ServerConnectionPools.ConnectionPoolName> pools = serverConnectionPools.getPoolsList();
            for (ServerConnectionPools.ConnectionPoolName pool : pools) {
                if (pConnectionPoolName.equals(pool.getBscsUser())) {
                    Map<String, String> poolMap = optionsMap.computeIfAbsent(pConnectionPoolName, k -> new HashMap<>());
                    populateOptionsMap(poolMap, pool);
                    break;
                }
            }
        }
    }

    protected void setOptionMapForPools() {
        List<ServerConnectionPools.ConnectionPoolName> pools = serverConnectionPools.getPoolsList();
        for (ServerConnectionPools.ConnectionPoolName pool : pools) {
            setOptionMap(pool.getBscsUser());
        }
    }

    private void populateOptionsMap(Map<String, String> map, Object settings) {
        Method[] methods = settings.getClass().getMethods();
        for (Method method : methods) {
            if (isGetter(method)) {
                String key = getPropertyName(method.getName());
                try {
                    Object value = method.invoke(settings);
                    if (value != null) {
                        map.put(key, String.valueOf(value));
                    } else {
                        map.put(key, String.valueOf(defaultOptionsMap.get(key)));
                    }
                } catch (Exception e) {
                    log.warn("Error invoking method: {}", method.getName(), e);
                }
            }
        }
    }

    private boolean isGetter(Method method) {
        return method.getName().startsWith("get")
                && method.getParameterCount() == 0
                && method.getReturnType() != void.class
                && !method.getName().equalsIgnoreCase("getClass");
    }

    private String getPropertyName(String getterName) {
        String property = getterName.substring(3);
        return Character.toLowerCase(property.charAt(0)) + property.substring(1);
    }

}

