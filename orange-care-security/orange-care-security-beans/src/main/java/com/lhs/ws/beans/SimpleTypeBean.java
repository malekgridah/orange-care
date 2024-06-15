package com.lhs.ws.beans;

import com.lhs.ccb.common.soi.SVLObject;

import java.io.Serializable;

public class SimpleTypeBean extends BaseSOIBean implements Serializable {
    private Object _object;

    public SimpleTypeBean() {
    }

    public SimpleTypeBean(Object var1) {
        this._object = var1;
    }

    public Object getSimpleTypeValue() {
        return this._object instanceof Character ? ((Character)this._object).toString() : this._object;
    }

    public void setSimpleTypeValue(Object var1) {
        this._object = var1;
    }

    public void setBeanValue(String var1, SVLObject var2) {
        var2.setValue(var1, this._object);
    }
}