package com.billcom.connectionpools.pools;


import com.billcom.connectionpools.config.ConnectionPoolServiceConfig;
import com.billcom.connectionpools.config.properties.ServerConnectionPools;
import com.lhs.ccb.cfw.cda.utility.CFWConfigurationException;
import com.lhs.ccb.cfw.cda.utility.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Log4j2
public class ServerConnectionPoolOptions {
    public static final String SERVER_CONNECTION_POOLS = "ServerConnectionPools";
    public static final String BSCS_USER_KEY = "BscsUser";
    public static final String CONNECTION_TIMEOUT_KEY = "ConnectionTimeOut";
    public static final String MAX_CONNECTIONS_KEY = "MaximumNumberOfConnections";
    public static final String MIN_CONNECTIONS_KEY = "MinimumNumberOfConnections";
    public static final String CONNECTION_ATTEMPTS_KEY = "NoOfConnectionAttempts";
    public static final String CONNECTION_ATTEMPT_INTERVAL_KEY = "ConnectionAttemptInterval";
    public static final String NAME_PREFIX = "ConnectionPool_";
    public static final String DEFAULT_POOL = "DefaultPoolSettings";
    public static final String PASSWORD_KEY = "BscsUserPassword";
    public static String[] OPTION_ARRAY = new String[]{"BscsUser", "BscsUserPassword", "ConnectionTimeOut", "MaximumNumberOfConnections", "MinimumNumberOfConnections", "NoOfConnectionAttempts", "ConnectionAttemptInterval"};
    public static final String EXT_CFW_ERROR_BUNDLE = "com.lhs.ccb.cfw.wcs.errorhandling.interfacebundle.ExtApplicationErrorBundle";
    private String _poolName = null;
    Map _optionsMap = new HashMap();
    static Map _defaultOptionsMap = new HashMap();

    private final ServerConnectionPools serverConnectionPools;
    private final ConnectionPoolServiceConfig connectionPoolService;

    @Autowired
    public ServerConnectionPoolOptions(ServerConnectionPools serverConnectionPools, ConnectionPoolServiceConfig connectionPoolService) {
        this.serverConnectionPools = serverConnectionPools;
        this.connectionPoolService = connectionPoolService;
    }

    public void setPoolName(String poolName) {
        this._poolName = poolName;
        if (_defaultOptionsMap.isEmpty()) {
            setOptionMap(null);
        }
        setOptionMap(poolName);
    }

    protected void setOptionMap(String pConnectionPoolName) {
        String poolKey;
        if (pConnectionPoolName == null) {
            poolKey = "DefaultPoolSettings";
        } else {
            poolKey = "ConnectionPool_" + pConnectionPoolName;
        }

        if (this.serverConnectionPools.getDefault().getPoolName() == null && !this.serverConnectionPools.getPoolNames().contains(poolKey)) {
            Log.SystemLogger.log(Level.SEVERE, "No connection pool configured for BSCSUser:" + pConnectionPoolName);
            throw new CFWConfigurationException("No Pool configured for user :" + pConnectionPoolName);
        } else {

            if (pConnectionPoolName == null) {
                _defaultOptionsMap = this.connectionPoolService.getDefaultOptionsMap();
            } else {
                this._optionsMap = this.connectionPoolService.getOptionsMap().get(pConnectionPoolName);
            }

        }
    }


    protected void setOptionValue(String pKey, Object pValue) {
        if (pKey != null && pValue != null) {
            this._optionsMap.put(pKey, pValue);
        }

    }

    public Object getOptionValue(String pKey) {
        Object option = this._optionsMap.get(pKey);
        if (null == option) {
            option = _defaultOptionsMap.get(pKey);
        }

        return option;
    }

    public String getConnectionPoolName() {
        return this._poolName;
    }
}
