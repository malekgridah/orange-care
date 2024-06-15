package com.billcom.connectionpools.pools;


import com.billcom.connectionpools.beans.ConnectionPoolName;
import com.billcom.connectionpools.config.SpringContext;
import com.billcom.connectionpools.config.pools.ConnectionPoolConfig;
import com.billcom.connectionpools.config.pools.DefaultPoolSettings;
import com.lhs.ccb.cfw.cda.utility.CFWConfigurationException;
import com.lhs.ccb.cfw.cda.utility.Log;
import com.lhs.ccb.func.ect.ComponentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class PoolOptions {

    public static final Logger logger = LogManager.getLogger(PoolOptions.class);
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
    Map<String, Object> _optionsMap = new HashMap<>();
    static Map<String, Object> _defaultOptionsMap = new HashMap<>();

    public PoolOptions(String pConnectionPoolName) {
        _poolName = pConnectionPoolName;
        if (_defaultOptionsMap.isEmpty()) {
            setOptionMap(null);
        }

        setOptionMap(pConnectionPoolName);
    }

    protected void setOptionMap(String pConnectionPoolName) {
        String poolKey;
        if (pConnectionPoolName == null) {
            poolKey = "DefaultPoolSettings";
        } else {
            poolKey = "ConnectionPool_" + pConnectionPoolName;
        }
        ConnectionPoolConfig connectionPoolConfig = null;
        DefaultPoolSettings defaultPoolSettings = null;
        try {
            connectionPoolConfig = SpringContext.getBean(ConnectionPoolConfig.class);
            defaultPoolSettings= SpringContext.getBean(DefaultPoolSettings.class);
        }catch (ComponentException var9){
            Log.SystemLogger.log(Level.INFO, "Problem in reading connection pools properties", var9);
        }

        if((connectionPoolConfig == null) && (defaultPoolSettings == null)) {
            Log.SystemLogger.log(Level.INFO, "No connection pool configured for BSCSUser:" + pConnectionPoolName);
            throw new CFWConfigurationException("No Pool configured for user :" + pConnectionPoolName);
        }else {
            if(poolKey.equals("DefaultPoolSettings")) {
                if (defaultPoolSettings.getConnectionTimeout() != null) {
                    _defaultOptionsMap.put(CONNECTION_TIMEOUT_KEY, defaultPoolSettings.getConnectionTimeout());
                }
                if (defaultPoolSettings.getMaxNumConnections() != null) {
                    _defaultOptionsMap.put(MAX_CONNECTIONS_KEY, defaultPoolSettings.getMaxNumConnections());
                }
                if (defaultPoolSettings.getMinNumConnections() != null) {
                    _defaultOptionsMap.put(MIN_CONNECTIONS_KEY, defaultPoolSettings.getMinNumConnections());
                }
                if (defaultPoolSettings.getNoOfConnectionAttempts() != null) {
                    _defaultOptionsMap.put(CONNECTION_ATTEMPTS_KEY, defaultPoolSettings.getNoOfConnectionAttempts());
                }
                if (defaultPoolSettings.getConnectionAttemptInterval() != null) {
                    _defaultOptionsMap.put(CONNECTION_ATTEMPT_INTERVAL_KEY, defaultPoolSettings.getConnectionAttemptInterval());
                }
            }else if(poolKey.equals("ConnectionPool_"+pConnectionPoolName)){

                List<ConnectionPoolName> connectionPoolNames = connectionPoolConfig.getPools();
                for (ConnectionPoolName poolName : connectionPoolNames) {
                    if(!pConnectionPoolName.trim().equals(poolName.getBscsUser()))
                        continue;
                    if(_optionsMap.get(BSCS_USER_KEY)!=null)
                        continue;
                    if (poolName.getBscsUser() != null) {
                        _optionsMap.put(BSCS_USER_KEY, poolName.getBscsUser());
                    }
                    if (poolName.getBscsUserPassword() != null) {
                        _optionsMap.put(PASSWORD_KEY, poolName.getBscsUserPassword());
                    }
                    if (poolName.getConnectionTimeout() != null) {
                        _optionsMap.put(CONNECTION_TIMEOUT_KEY, poolName.getConnectionTimeout());
                    }
                    if (poolName.getMaxNumConnections() != null) {
                        _optionsMap.put(MAX_CONNECTIONS_KEY, poolName.getMaxNumConnections());
                    }
                    if (poolName.getMinNumConnections() != null) {
                        _optionsMap.put(MIN_CONNECTIONS_KEY, poolName.getMinNumConnections());
                    }
                    if (poolName.getNoOfConnectionAttempts() != null) {
                        _optionsMap.put(CONNECTION_ATTEMPTS_KEY, poolName.getNoOfConnectionAttempts());
                    }
                    if (poolName.getConnectionAttemptInterval() != null) {
                        _optionsMap.put(CONNECTION_ATTEMPT_INTERVAL_KEY, poolName.getConnectionAttemptInterval());
                    }
                }


            }
        }

    }


    protected void setOptionValue(String pKey, Object pValue) {
        if (pKey != null && pValue != null) {
            _optionsMap.put(pKey, pValue);
        }

    }

    public Object getOptionValue(String pKey) {
        Object option = _optionsMap.get(pKey);
        if (null == option) {
            option = _defaultOptionsMap.get(pKey);
        }

        return option;
    }

    public String getConnectionPoolName() {
        return _poolName;
    }
}
