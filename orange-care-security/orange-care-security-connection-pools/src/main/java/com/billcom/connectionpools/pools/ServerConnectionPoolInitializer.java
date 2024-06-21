package com.billcom.connectionpools.pools;

import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

public class ServerConnectionPoolInitializer extends ContextLoaderListener {
    private static boolean _isInitialized = false;

    private final ServerDefaultConnectionProvider serverDefaultConnectionProvider;
    private final ServerConnectionPoolManager serverConnectionPoolManager;

    public ServerConnectionPoolInitializer(ServerDefaultConnectionProvider serverDefaultConnectionProvider, ServerConnectionPoolManager serverConnectionPoolManager) {
        this.serverDefaultConnectionProvider = serverDefaultConnectionProvider;
        this.serverConnectionPoolManager = serverConnectionPoolManager;
    }

    public static boolean isInitialized() {
        return _isInitialized;
    }

    public void contextDestroyed(ServletContextEvent evt) {
        serverDefaultConnectionProvider.disposeAllConnections();
        _isInitialized = false;
    }

    public void contextInitialized(ServletContextEvent evt) {
        serverConnectionPoolManager.startPools();
        _isInitialized = true;
    }
}
