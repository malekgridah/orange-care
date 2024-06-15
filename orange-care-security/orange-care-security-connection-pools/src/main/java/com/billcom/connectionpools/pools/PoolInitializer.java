package com.billcom.connectionpools.pools;

import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

public class PoolInitializer extends ContextLoaderListener {
    private static boolean _isInitialized = false;

    public PoolInitializer() {
    }

    public static boolean isInitialized() {
        return _isInitialized;
    }

    public void contextDestroyed(ServletContextEvent evt) {
        DefaultPoolProvider.getInstance().disposeAllConnections();
        _isInitialized = false;
    }

    public void contextInitialized(ServletContextEvent evt) {
        PoolManager.getInstance().startPools();
        _isInitialized = true;
    }
}
