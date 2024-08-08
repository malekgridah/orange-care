package com.billcom.authentication.beans;

import com.lhs.ccb.common.soi.ExchangeFormatFactory;
import com.lhs.ccb.common.soi.SVLObject;
import com.lhs.ccb.common.soi.SVLObjectList;
import com.lhs.ws.beans.BaseSOIBean;

import java.io.Serializable;

public class RoleBeanOut extends BaseSOIBean implements Serializable {
    private String _role;

    public RoleBeanOut() {
    }

    public RoleBeanOut(SVLObject pObject) throws Exception {
        this.setSVLObject(pObject);
    }

    public static RoleBeanOut[] createList(int size) {
        RoleBeanOut[] list = new RoleBeanOut[size];
        return list;
    }

    public static RoleBeanOut[] createList(SVLObjectList pList) throws Exception {
        if (null == pList) {
            return null;
        } else {
            int size = pList.size();
            RoleBeanOut[] list = new RoleBeanOut[size];

            for(int indx = 0; indx < pList.size(); ++indx) {
                list[indx] = new RoleBeanOut(pList.get(indx));
            }

            return list;
        }
    }

    public String getRole() {
        return this._role;
    }

    public void setRole(String pRole) {
        this._role = pRole;
        if (null == pRole) {
            this._svlObject.removeAttribute("ROLE_ID_PUB");
        } else {
            this._svlObject.setValue("ROLE_ID_PUB", pRole);
        }
    }

    public void reset() {
        this.setRole((String) null);
    }

    protected void setSVLObject(SVLObject pObject) throws Exception {
        if (null == pObject) {
            this.reset();
            this._svlObject = ExchangeFormatFactory.instance().createSVLObject();
        } else {

            if (pObject.isAttributeSet("ROLE_ID_PUB")) {
                Object s = pObject.getValue("ROLE_ID_PUB");
                if (s == null) {
                    this.setRole(null);
                } else {
                    this.setRole(s.toString());
                }
            }

            super.setSVLObject(pObject);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Role Out {");
        if (null != this._role) {
            sb.append("_role=" + this._role);
        }

        sb.append("}");
        return sb.toString();
    }
}
