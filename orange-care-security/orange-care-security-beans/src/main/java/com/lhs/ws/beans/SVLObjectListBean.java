package com.lhs.ws.beans;

import com.lhs.ccb.common.soi.ExchangeFormatFactory;
import com.lhs.ccb.common.soi.SVLObjectList;

import java.io.Serializable;

public class SVLObjectListBean extends BaseSOIBean implements Serializable {
    private SVLObjectBean[] _objectArray;
    protected SVLObjectList _svlObjectList = null;

    public SVLObjectListBean() {
        this._svlObjectList = ExchangeFormatFactory.instance().createSVLObjectList();
    }

    public SVLObjectListBean(SVLObjectList var1) throws Exception {
        this._svlObjectList = var1;
        this._objectArray = new SVLObjectBean[var1.size()];

        for(int var2 = 0; var2 < this._objectArray.length; ++var2) {
            this._objectArray[var2] = new SVLObjectBean(var1.get(var2));
        }

    }

    protected SVLObjectList getSVLObjectList() {
        return this._svlObjectList;
    }

    public SVLObjectBean[] getObjectArray() {
        return this._objectArray;
    }

    public void setObjectArray(SVLObjectBean[] var1) {
        this._objectArray = var1;
        if (var1 != null) {
            for(int var2 = 0; var2 < var1.length; ++var2) {
                this._svlObjectList.add(var1[var2].getSVLObject());
            }

        }
    }
}
