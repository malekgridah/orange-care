package com.billcom.connectionpools.pools;


import com.billcom.connectionpools.config.SpringContext;
import com.billcom.connectionpools.config.pools.ConfiguredPoolNames;
import com.billcom.connectionpools.config.pools.DefaultUserPool;
import com.lhs.ccb.cfw.cda.servicelayer.connectionpool.Connection;
import com.lhs.ccb.cfw.cda.servicelayer.connectionpool.ConnectionPool;
import com.lhs.ccb.cfw.cda.utility.CFWConfigurationException;
import com.lhs.ccb.cfw.cda.utility.CFWRuntimeException;
import com.lhs.ccb.cfw.cda.utility.Log;
import com.lhs.ccb.cfw.cda.utility.UserPropertiesFacade;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class PoolManager {
    Map<String, Object> _connectionPoolMap = new HashMap<>();
    protected static PoolManager _instance = null;

    public static PoolManager getInstance() {
        if (_instance == null) {
            _instance = new PoolManager();
        }

        return _instance;
    }

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
            DefaultUserPool defaultPoolUser = SpringContext.getBean(DefaultUserPool.class);
            pool = (ConnectionPool)this._connectionPoolMap.get(defaultPoolUser.getDefaultConnectionUser());
        }

        if (pool == null) {
            throw new CFWRuntimeException("ConnectionPoolNotConfigured for : " + pBSCSUserName);
        } else {
            return pool.getConnection();
        }
    }

    public void startPools() {
        ConfiguredPoolNames configuredPoolNames = SpringContext.getBean(ConfiguredPoolNames.class);
        StringTokenizer tokenizer = new StringTokenizer(configuredPoolNames.getConnectionPoolNames(), ",");

        while(tokenizer.hasMoreTokens()) {
            String element = tokenizer.nextToken();
            if (element != null) {
                element = element.trim();
                if (null == this._connectionPoolMap.get(element)) {
                    try {
                        PoolOptions poolOptions = new PoolOptions(element);
                        ConnectionPool pool = new ConnectionPoolImplemnt(poolOptions);
                        this._connectionPoolMap.put(element, pool);
                    } catch (CFWConfigurationException var6) {
                        Log.SystemLogger.log(Level.SEVERE, "Configuration problem : ", var6);
                    }
                }
            }
        }

    }

    public void stopConnectionPools() {
        Iterator iter = this._connectionPoolMap.keySet().iterator();

        while(iter.hasNext()) {
            String key = (String)iter.next();
            ConnectionPool connPool = (ConnectionPool)this._connectionPoolMap.get(key);
            connPool.disposeConnectionPool();
        }

        this._connectionPoolMap.clear();
    }

    protected PoolManager() {
    }
}
