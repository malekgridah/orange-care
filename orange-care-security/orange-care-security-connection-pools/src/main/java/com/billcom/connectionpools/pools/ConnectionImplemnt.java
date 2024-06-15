package com.billcom.connectionpools.pools;

import com.lhs.ccb.cfw.cda.servicelayer.AccessorProvider;
import com.lhs.ccb.cfw.cda.servicelayer.connectionpool.Connection;
import com.lhs.ccb.cfw.cda.utility.Log;
import com.lhs.ccb.common.soi.AccessorFactory;
import com.lhs.ccb.common.soi.ServiceAccessor;
import com.lhs.ccb.common.soi.UnknownComponentException;
import com.lhs.ccb.func.corba.CORBAAdapter;
import com.lhs.ccb.func.util.DoubleKeyMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import org.omg.CORBA.Object;

public class ConnectionImplemnt implements Connection, AccessorProvider {

    Logger logger = LogManager.getLogger(ConnectionImplemnt.class);
    protected final AccessorFactory _serviceFactory;
    protected final long _creationTimeStamp;
    protected long _lastUsedTimeStamp;
    protected final Map<String, String> _properties = new HashMap<>();
    private final DoubleKeyMap<String, String, ServiceAccessor> _serviceAccessorMap = new DoubleKeyMap<>(8, 0.75F, 4, 0.75F);

    public ConnectionImplemnt(AccessorFactory pAccessorFactory, String pBscsUserName) {
        this._serviceFactory = pAccessorFactory;
        this._creationTimeStamp = System.currentTimeMillis();
        this._lastUsedTimeStamp = this._creationTimeStamp;
        this._properties.put("BSCSUser", pBscsUserName);
    }

    public void dispose() {
        try {
            this.getServiceFactory().dispose();
        } catch (Exception var2) {
            Log.SystemLogger.log(Level.SEVERE, "Disposing connection failed", var2);
        }

    }

    public long getCreationTime() {
        return this._creationTimeStamp;
    }

    public long getIdlePeriod() {
        return System.currentTimeMillis() - this._lastUsedTimeStamp;
    }

    public Map getProperties() {
        return this._properties;
    }

    public AccessorFactory getServiceFactory() {
        return this._serviceFactory;
    }

    public ServiceAccessor fetchServiceAccessor(String pSoiName, String pSoiVersion) throws UnknownComponentException, SecurityException {
        this._lastUsedTimeStamp = System.currentTimeMillis();
        ServiceAccessor accessor = this._serviceAccessorMap.get(pSoiName, pSoiVersion);
        if (accessor == null) {
            accessor = this.getServiceFactory().getServiceAccessor(pSoiName, pSoiVersion);
            this._serviceAccessorMap.put(pSoiName, pSoiVersion, accessor);
        }

        return accessor;
    }

    public void dropServiceAccessor(ServiceAccessor pAccessor) {
        pAccessor.dispose();
    }

    public void release() {
        Iterator<ServiceAccessor> allAccesors = this._serviceAccessorMap.values().iterator();

        while(allAccesors.hasNext()) {
            ServiceAccessor accessor = allAccesors.next();
            this.dropServiceAccessor(accessor);
        }

        this._serviceAccessorMap.clear();
    }

    public boolean isConnectionValid() {
        Object corbaObject = this.getServiceFactory().getCorbaObject();
        boolean res = CORBAAdapter.instance().isObjectAlive(corbaObject);
        return res;
    }


}
