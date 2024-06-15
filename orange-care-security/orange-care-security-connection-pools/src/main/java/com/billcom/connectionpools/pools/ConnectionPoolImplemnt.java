package com.billcom.connectionpools.pools;

import com.billcom.connectionpools.beans.ConnectionPoolName;
import com.billcom.connectionpools.config.SpringContext;
import com.billcom.connectionpools.config.pools.ConnectionPoolConfig;
import com.lhs.ccb.cfw.cda.servicelayer.AccessorProvider;
import com.lhs.ccb.cfw.cda.servicelayer.SecurityRuntimeException;
import com.lhs.ccb.cfw.cda.servicelayer.ServiceRuntimeException;
import com.lhs.ccb.cfw.cda.servicelayer.SoiServerGateway;
import com.lhs.ccb.cfw.cda.servicelayer.connectionpool.Connection;
import com.lhs.ccb.cfw.cda.servicelayer.connectionpool.ConnectionPool;
import com.lhs.ccb.cfw.cda.session.ApplicationSettings;
import com.lhs.ccb.cfw.cda.session.ConnectionFailedException;
import com.lhs.ccb.cfw.cda.session.ContainerFactory;
import com.lhs.ccb.cfw.cda.utility.Log;
import com.lhs.ccb.common.security.PermissionChecker;
import com.lhs.ccb.common.security.RemotePermissionCheckerI;
import com.lhs.ccb.common.soi.SecurityException;
import com.lhs.ccb.common.soi.ServiceAccessor;
import com.lhs.ccb.common.soi.UnknownComponentException;
import com.lhs.ccb.func.ect.ComponentException;
import com.lhs.ccb.func.ect.SystemException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

public class ConnectionPoolImplemnt implements ConnectionPool{

    private int _minConnections = 1;
    private int _maxConnections = 5;
    private int _connectionAttempts = 3;
    private int _connectionAttemptTimeInterval = 20;
    private int _connectionTimeout = 1000;
    private String _BSCSUserName;
    private String _connectionPoolName;
    private static final Logger SYSTEM_LOGGER = LogManager.getLogger(ConnectionPoolImplemnt.class);
    private String _BSCSUserPassword;
    private List<Connection> _freeConnections = new ArrayList<>();
    private List<Connection> _usedConnections = new ArrayList<>();

    private boolean orbIsDown = false;


    public ConnectionPoolImplemnt(PoolOptions pConnectionPoolOptions) {
        if (pConnectionPoolOptions != null) {
            ConnectionPoolConfig poolConfig = SpringContext.getBean(ConnectionPoolConfig.class);
            List<ConnectionPoolName> listPools = poolConfig.getPools();
            ConnectionPoolName poolConnect = new ConnectionPoolName();
            for (ConnectionPoolName pool : listPools) {
                if(pConnectionPoolOptions.getConnectionPoolName().equals(pool.getBscsUser())){
                    poolConnect = pool;
                    break;
                }
            }
            this._connectionPoolName = pConnectionPoolOptions.getConnectionPoolName();

            if (poolConnect.getMinNumConnections() != null) {
                this._minConnections = poolConnect.getMinNumConnections();
            }

            if (poolConnect.getMaxNumConnections() != null) {
                this._maxConnections = poolConnect.getMaxNumConnections();
            }

            if (poolConnect.getNoOfConnectionAttempts() != null) {
                this._connectionAttempts = poolConnect.getNoOfConnectionAttempts();
            }

            if (poolConnect.getConnectionAttemptInterval() != null) {
                this._connectionAttemptTimeInterval = poolConnect.getConnectionAttemptInterval();
            }

            if (poolConnect.getConnectionTimeout() != null) {
                this._connectionTimeout = poolConnect.getConnectionTimeout();
            }

            if (poolConnect.getBscsUser() != null) {
                this._BSCSUserName = poolConnect.getBscsUser();
            }

            if (poolConnect.getBscsUserPassword() != null) {
                this._BSCSUserPassword = poolConnect.getBscsUserPassword();
            }
        }

    }

    @Override
    public synchronized Connection getConnection() throws ServiceRuntimeException {
        Connection con;
        for(int attemptCounter = 0; (con = this.getConnectionFromPool()) == null && attemptCounter < this._connectionAttempts; ++attemptCounter) {
            try {
                if(this.orbIsDown){
                    throw new ServiceRuntimeException("Connection failed");
                }
                this.wait(this._connectionAttemptTimeInterval);
            } catch (InterruptedException ignored) {
            }
        }

        if (con == null) {
            throw new ServiceRuntimeException("NoConnectionAvailableInPool_Key");
        } else {
            return con;
        }
    }

    protected Connection getConnectionFromPool() throws ServiceRuntimeException {
        Connection con = null;
        orbIsDown = false;
        if (!this._freeConnections.isEmpty()) {
            for(int i = 0; i < this._freeConnections.size(); ++i) {
                con = this._freeConnections.get(0);
                this._freeConnections.remove(0);
                if (con.isConnectionValid()) {
                    break;
                }
                con = null;
                orbIsDown = true;
            }
        }

        if(orbIsDown && con == null) {
            return null;
        }

        if (con == null && (this._maxConnections == 0 || this._usedConnections.size() < this._maxConnections)) {
            try {
                con = this.createConnection();
            } catch (ConnectionFailedException var3) {
                SYSTEM_LOGGER.warn("Connection creation failed for pool" + this.getPoolName(), var3);
                throw new ServiceRuntimeException(var3);
            }
        }

        if (con != null) {
            this._usedConnections.add(con);
        }
        return con;
    }

    protected Connection createConnection() throws ConnectionFailedException {
        ContainerFactory connection = SoiServerGateway.instance().connect(this._BSCSUserName, this._BSCSUserPassword);
        Connection conn = new ConnectionImplemnt(connection.getServiceFactory(), this._BSCSUserName);
        this.initSeviceAccessorForSecurity(conn);
        return conn;
    }

    public synchronized void releaseConnection(Connection pConnection) {
        if (pConnection != null && this._usedConnections.contains(pConnection)) {
            try {
                pConnection.release();
                if (!this._freeConnections.contains(pConnection)) {
                    this._freeConnections.add(pConnection);
                }

                this._usedConnections.remove(pConnection);
            } catch (SystemException var3) {
                this.disposeConnection(pConnection);
            }

            Logger var10000 = SYSTEM_LOGGER;
            String var10002 = this.getPoolName();
            var10000.debug("After release No of connections available in pool " + var10002 + " : " + this._freeConnections.size());
            SYSTEM_LOGGER.debug("After release No of connections in use: " + this._usedConnections.size());

            this.notifyAll();
        }
    }

    public void disposeConnection(Connection pConnection) {
        this._freeConnections.remove(pConnection);
        this._usedConnections.remove(pConnection);
        pConnection.dispose();
    }

    public int getConnectionTimeOut() {
        return this._connectionTimeout;
    }

    public void disposeConnectionPool() {
        Iterator<Connection> connectionsIter = this._freeConnections.iterator();

        Connection connection;
        while(connectionsIter.hasNext()) {
            connection = connectionsIter.next();
            connection.dispose();
        }

        this._freeConnections.clear();
        connectionsIter = this._usedConnections.iterator();

        while(connectionsIter.hasNext()) {
            connection = connectionsIter.next();
            connection.dispose();
        }

        this._usedConnections.clear();
        SYSTEM_LOGGER.info("Closed connections for pool " + this.getPoolName());

    }

    public String getPoolName() {
        return this._connectionPoolName;
    }

    public int getMaxConnections() {
        return this._maxConnections;
    }

    public int getMinConnections() {
        return this._minConnections;
    }

    public int getSize() {
        return this._freeConnections.size();
    }

    public int getConnectionAttempts() {
        return this._connectionAttempts;
    }

    public int getConnectionAttemptTimeInterval() {
        return this._connectionAttemptTimeInterval;
    }

    private void initSeviceAccessorForSecurity(Connection pNewConnection) {
        ServiceAccessor accessor = null;
        String soiName = ApplicationSettings.instance().getAttribute("SoiNameForSecurity");
        String soiVersion = ApplicationSettings.instance().getAttribute("SoiVersionForSecurity");
        if (pNewConnection instanceof AccessorProvider) {
            try {
                accessor = ((AccessorProvider)pNewConnection).fetchServiceAccessor(soiName, soiVersion);
            } catch (UnknownComponentException var7) {
                Log.CFWLogger.log(Level.SEVERE, "Unknown Component", var7);
                throw new ServiceRuntimeException(var7);
            } catch (SecurityException var8) {
                throw new SecurityRuntimeException(var8);
            }
        }

        try {
            ((RemotePermissionCheckerI) PermissionChecker.getInstance()).init(accessor, SoiServerGateway.instance().getApplicationName());
        } catch (ComponentException var6) {
            Log.CFWLogger.log(Level.SEVERE, "Permission checker instaniation failed", var6);
        }

    }
}
