package com.lhs.ws.beans;


import com.lhs.ccb.common.soi.ExchangeFormatFactory;
import com.lhs.ccb.common.soi.SVLObject;

import java.io.Serializable;

public class BaseSOIBean implements Serializable {
    protected SVLObject _svlObject = ExchangeFormatFactory.instance().createSVLObject();

    public BaseSOIBean() {
    }

    protected SVLObject getSVLObject() {
        return this._svlObject;
    }

    protected void setSVLObject(SVLObject var1) throws Exception {
        this._svlObject = var1;
    }
}