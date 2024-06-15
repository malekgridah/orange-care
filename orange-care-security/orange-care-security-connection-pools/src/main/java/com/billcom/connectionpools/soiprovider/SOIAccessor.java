package com.billcom.connectionpools.soiprovider;

import com.billcom.connectionpools.pools.PoolManager;
import com.lhs.ccb.cfw.cda.servicelayer.connectionpool.Connection;
import com.lhs.ccb.cfw.cda.session.ContainerFactory;
import com.lhs.ccb.cfw.cda.session.DomainlayerFacade;
import com.lhs.ccb.cfw.cda.session.ServerFacade;
import com.lhs.ccb.common.soi.ServiceAccessor;

public class SOIAccessor {
    private ServiceAccessor _accessor = null;
    private ContainerFactory _factory = null;
    private Connection _pooledConnection = null;

    public SOIAccessor(ServiceAccessor var1, ContainerFactory var2, Connection var3) {
        this._accessor = var1;
        this._factory = var2;
        this._pooledConnection = var3;
    }

    public void release() {
        if (null != this._pooledConnection) {
            PoolManager.getInstance().releaseConnection(this._pooledConnection);
        } else {
            this._accessor.dispose();
            if (DomainlayerFacade.instance().isInitialized()) {
                DomainlayerFacade.instance().logout(this._factory);
            } else {
                ServerFacade.instance().logout(this._factory);
            }
        }

    }

    public ServiceAccessor getServiceAccessor() {
        return this._accessor;
    }

    public ContainerFactory getAccessorFactory() {
        return this._factory;
    }

    public Connection getPooledConnection() {
        return this._pooledConnection;
    }

    public boolean isPooledConnection() {
        return null != this._pooledConnection;
    }
}

