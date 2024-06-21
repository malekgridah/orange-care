package com.billcom.connectionpools.pools;

import com.lhs.ccb.cfw.cda.servicelayer.ServiceRuntimeException;
import com.lhs.ccb.cfw.cda.servicelayer.connectionpool.Connection;
import com.lhs.ccb.cfw.cda.servicelayer.connectionpool.ConnectionProvider;

public class ServerDefaultConnectionProvider implements ConnectionProvider {
    private boolean _useConnectionPool = false;

    private final ServerConnectionPoolManager serverConnectionPoolManager;

    public ServerDefaultConnectionProvider(ServerConnectionPoolManager serverConnectionPoolManager) {
        this.serverConnectionPoolManager = serverConnectionPoolManager;

        if (ServerConnectionPoolInitializer.isInitialized()) {
            this._useConnectionPool = true;
        }
    }

    public Connection getConnection(String pConnectionType) throws ServiceRuntimeException {
        if ("SERVER_CONNECTION".equalsIgnoreCase(pConnectionType)) {
            if (this._useConnectionPool) {
                return serverConnectionPoolManager.getConnection();
            }
        } else if ("DOMAIN_LAYER".equalsIgnoreCase(pConnectionType)) {
            return null;
        }

        return null;
    }

    public void releaseConnection(Connection pConnection, String pConnectionType) {
        if ("SERVER_CONNECTION".equalsIgnoreCase(pConnectionType)) {
            if (this._useConnectionPool) {
                serverConnectionPoolManager.releaseConnection(pConnection);
            }
        } else if ("DOMAIN_LAYER".equalsIgnoreCase(pConnectionType)) {
        }

    }

    public boolean useConnectionPool() {
        return this._useConnectionPool;
    }

    public void disposeAllConnections() {
        if (this._useConnectionPool) {
            serverConnectionPoolManager.stopConnectionPools();
        }

    }

    public Connection getConnection(String pConnectionType, String pBSCSUser) throws ServiceRuntimeException {
        return "SERVER_CONNECTION".equalsIgnoreCase(pConnectionType) && this._useConnectionPool ? serverConnectionPoolManager.getConnection(pBSCSUser) : null;
    }
}
