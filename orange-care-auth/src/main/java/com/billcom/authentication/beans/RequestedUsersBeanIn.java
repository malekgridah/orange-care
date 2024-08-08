package com.billcom.authentication.beans;


import com.lhs.ccb.common.soi.ExchangeFormatFactory;
import com.lhs.ccb.common.soi.SVLObject;
import com.lhs.ccb.common.soi.SVLObjectList;
import com.lhs.ws.beans.BaseSOIBean;

import java.io.Serializable;

public class RequestedUsersBeanIn extends BaseSOIBean implements Serializable {
    private String _username;

    public RequestedUsersBeanIn() {
    }

    public RequestedUsersBeanIn(SVLObject pObject) throws Exception {
        this.setSVLObject(pObject);
    }

    public static RequestedUsersBeanIn[] createList(int size) {
        RequestedUsersBeanIn[] list = new RequestedUsersBeanIn[size];
        return list;
    }

    public static RequestedUsersBeanIn[] createList(SVLObjectList pList) throws Exception {
        if (null == pList) {
            return null;
        } else {
            int size = pList.size();
            RequestedUsersBeanIn[] list = new RequestedUsersBeanIn[size];

            for(int indx = 0; indx < pList.size(); ++indx) {
                list[indx] = new RequestedUsersBeanIn(pList.get(indx));
            }

            return list;
        }
    }

    public String getUsername() {
        return this._username;
    }

    public void setUsername(String pUsername) {
        this._username = pUsername;
        if (null == pUsername) {
            this._svlObject.removeAttribute("USERNAME");
        } else {
            this._svlObject.setValue("USERNAME", pUsername);
        }
    }

    public void reset() {
        this.setUsername((String)null);
    }

    protected void setSVLObject(SVLObject pObject) throws Exception {
        if (null == pObject) {
            this.reset();
            this._svlObject = ExchangeFormatFactory.instance().createSVLObject();
        } else {
            if (pObject.isAttributeSet("USERNAME")) {
                Object s = pObject.getValue("USERNAME");
                if (s == null) {
                    this.setUsername((String)null);
                } else {
                    this.setUsername(s.toString());
                }
            }

            super.setSVLObject(pObject);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("RequestedUsersBeanIn {");
        if (null != this._username) {
            sb.append("_username=" + this._username);
        }

        sb.append("}");
        return sb.toString();
    }
}
