package com.billcom.connectionpools.pools;

import com.lhs.ccb.cfw.cda.servicelayer.ServiceRuntimeException;
import com.lhs.ccb.cfw.cda.servicelayer.connectionpool.Connection;
import com.lhs.ccb.cfw.cda.servicelayer.connectionpool.ConnectionProvider;

public class DefaultPoolProvider implements ConnectionProvider {
    private boolean _useConnectionPool = false;
    protected static ConnectionProvider _instance = null;

    public static ConnectionProvider getInstance() {
        if (_instance == null) {
            _instance = new DefaultPoolProvider();
        }

        return _instance;
    }

    protected DefaultPoolProvider() {
        if (PoolInitializer.isInitialized()) {
            this._useConnectionPool = true;
        }

    }

    public Connection getConnection(String pConnectionType) throws ServiceRuntimeException {
        if ("SERVER_CONNECTION".equalsIgnoreCase(pConnectionType)) {
            if (this._useConnectionPool) {
                return PoolManager.getInstance().getConnection();
            }
        } else if ("DOMAIN_LAYER".equalsIgnoreCase(pConnectionType)) {
            return null;
        }

        return null;
    }

    public void releaseConnection(Connection pConnection, String pConnectionType) {
        if ("SERVER_CONNECTION".equalsIgnoreCase(pConnectionType)) {
            if (this._useConnectionPool) {
                PoolManager.getInstance().releaseConnection(pConnection);
            }
        } else if ("DOMAIN_LAYER".equalsIgnoreCase(pConnectionType)) {
        }

    }

    public boolean useConnectionPool() {
        return this._useConnectionPool;
    }

    public void disposeAllConnections() {
        if (this._useConnectionPool) {
            PoolManager.getInstance().stopConnectionPools();
        }

    }

    public Connection getConnection(String pConnectionType, String pBSCSUser) throws ServiceRuntimeException {
        return "SERVER_CONNECTION".equalsIgnoreCase(pConnectionType) && this._useConnectionPool ? PoolManager.getInstance().getConnection(pBSCSUser) : null;
    }
}