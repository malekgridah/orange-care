package com.billcom.authentication.beans;

import com.lhs.ccb.common.soi.ExchangeFormatFactory;
import com.lhs.ccb.common.soi.SVLObject;
import com.lhs.ccb.common.soi.SVLObjectList;
import com.lhs.ws.beans.BaseSOIBean;

import java.io.Serializable;

public class RequestedGroupsBeanIn extends BaseSOIBean implements Serializable {
    private String _groupname;

    public RequestedGroupsBeanIn() {
    }

    public RequestedGroupsBeanIn(SVLObject pObject) throws Exception {
        this.setSVLObject(pObject);
    }

    public static RequestedGroupsBeanIn[] createList(int size) {
        RequestedGroupsBeanIn[] list = new RequestedGroupsBeanIn[size];
        return list;
    }

    public static RequestedGroupsBeanIn[] createList(SVLObjectList pList) throws Exception {
        if (null == pList) {
            return null;
        } else {
            int size = pList.size();
            RequestedGroupsBeanIn[] list = new RequestedGroupsBeanIn[size];

            for(int indx = 0; indx < pList.size(); ++indx) {
                list[indx] = new RequestedGroupsBeanIn(pList.get(indx));
            }

            return list;
        }
    }

    public String getGroupname() {
        return this._groupname;
    }

    public void setGroupname(String pGroupname) {
        this._groupname = pGroupname;
        if (null == pGroupname) {
            this._svlObject.removeAttribute("GROUPNAME");
        } else {
            this._svlObject.setValue("GROUPNAME", pGroupname);
        }
    }

    public void reset() {
        this.setGroupname((String)null);
    }

    protected void setSVLObject(SVLObject pObject) throws Exception {
        if (null == pObject) {
            this.reset();
            this._svlObject = ExchangeFormatFactory.instance().createSVLObject();
        } else {
            if (pObject.isAttributeSet("GROUPNAME")) {
                Object s = pObject.getValue("GROUPNAME");
                if (s == null) {
                    this.setGroupname((String)null);
                } else {
                    this.setGroupname(s.toString());
                }
            }

            super.setSVLObject(pObject);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("RequestedGroupsBeanIn {");
        if (null != this._groupname) {
            sb.append("_groupname=" + this._groupname);
        }

        sb.append("}");
        return sb.toString();
    }
}
