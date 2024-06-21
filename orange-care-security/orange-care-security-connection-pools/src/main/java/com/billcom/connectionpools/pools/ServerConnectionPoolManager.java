package com.billcom.connectionpools.pools;

import com.billcom.connectionpools.config.properties.ServerConnectionPools;
import com.lhs.ccb.cfw.cda.servicelayer.connectionpool.Connection;
import com.lhs.ccb.cfw.cda.servicelayer.connectionpool.ConnectionPool;
import com.lhs.ccb.cfw.cda.utility.CFWConfigurationException;
import com.lhs.ccb.cfw.cda.utility.CFWRuntimeException;
import com.lhs.ccb.cfw.cda.utility.Log;
import com.lhs.ccb.cfw.cda.utility.UserPropertiesFacade;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class ServerConnectionPoolManager {

    private final ServerConnectionPools serverConnectionPools;
    private final ServerConnectionPoolOptions serverConnectionPoolOptions;

    public ServerConnectionPoolManager(ServerConnectionPools serverConnectionPools,
                                       ServerConnectionPoolOptions serverConnectionPoolOptions) {
        this.serverConnectionPools = serverConnectionPools;
        this.serverConnectionPoolOptions = serverConnectionPoolOptions;
    }

    Map _connectionPoolMap = new HashMap();
    protected static ServerConnectionPoolManager _instance = null;

    public void releaseConnection(Connection pConnection) {
        if (pConnection != null) {
            String userName = (String)pConnection.getProperties().get("BSCSUser");
            ConnectionPool pool = (ConnectionPool)this._connectionPoolMap.get(userName);
            if (pool != null) {
                pool.releaseConnection(pConnection);
            }

        }
    }

    public Connection getConnection() {
        String userName = UserPropertiesFacade.instance().getUserName();
        return this.getConnection(userName);
    }

    public Connection getConnection(String pBSCSUserName) {
        ConnectionPool pool = (ConnectionPool)this._connectionPoolMap.get(pBSCSUserName);
        if (pool == null) {
            String defaultPoolUser = this.serverConnectionPools.getDefault().getPoolName();
            pool = (ConnectionPool)this._connectionPoolMap.get(defaultPoolUser);
        }

        if (pool == null) {
            throw new CFWRuntimeException("ConnectionPoolNotConfigured for : " + pBSCSUserName);
        } else {
            return pool.getConnection();
        }
    }

    public void startPools() {
        String configuredPoolNames = String.join(",", this.serverConnectionPools.getPoolNames());
        StringTokenizer tokenizer = new StringTokenizer(configuredPoolNames, ",");

        while(tokenizer.hasMoreTokens()) {
            String element = tokenizer.nextToken();
            if (element != null) {
                element = element.trim();
                if (null == this._connectionPoolMap.get(element)) {
                    try {

                        serverConnectionPoolOptions.setPoolName(element);
                        ConnectionPool pool = new ServerConnectionPoolImpl(serverConnectionPoolOptions);
                        this._connectionPoolMap.put(element, pool);
                    } catch (CFWConfigurationException var6) {
                        CFWConfigurationException e = var6;
                        Log.SystemLogger.log(Level.SEVERE, "Configuration problem : ", e);
                    }
                }
            }
        }

    }

    public void stopConnectionPools() {
        for (Object o : this._connectionPoolMap.keySet()) {
            String key = (String) o;
            ConnectionPool connPool = (ConnectionPool) this._connectionPoolMap.get(key);
            connPool.disposeConnectionPool();
        }

        this._connectionPoolMap.clear();
    }
}
