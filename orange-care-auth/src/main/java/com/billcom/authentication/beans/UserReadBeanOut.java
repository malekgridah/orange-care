package com.billcom.authentication.beans;

import com.lhs.ccb.common.soi.ExchangeFormatFactory;
import com.lhs.ccb.common.soi.SVLObject;
import com.lhs.ccb.common.soi.SVLObjectList;
import com.lhs.ws.beans.BaseSOIBean;
import com.lhs.ws.beans.BeanSOIConverter;

import java.io.Serializable;

public class UserReadBeanOut extends BaseSOIBean implements Serializable {
    private UsersBeanOut[] _users;

    public UserReadBeanOut() {
    }

    public UserReadBeanOut(SVLObject pObject) throws Exception {
        this.setSVLObject(pObject);
    }

    public static UserReadBeanOut[] createList(int size) {
        UserReadBeanOut[] list = new UserReadBeanOut[size];
        return list;
    }

    public static UserReadBeanOut[] createList(SVLObjectList pList) throws Exception {
        if (null == pList) {
            return null;
        } else {
            int size = pList.size();
            UserReadBeanOut[] list = new UserReadBeanOut[size];

            for(int indx = 0; indx < pList.size(); ++indx) {
                list[indx] = new UserReadBeanOut(pList.get(indx));
            }

            return list;
        }
    }

    public UsersBeanOut[] getUsers() {
        return this._users;
    }

    public void setUsers(UsersBeanOut[] pUsers) {
        this._users = pUsers;
        if (null == pUsers) {
            this._svlObject.removeAttribute("USERS");
        } else {
            SVLObjectList list = ExchangeFormatFactory.instance().createSVLObjectList();

            for(int i = 0; i < pUsers.length; ++i) {
                list.add(BeanSOIConverter.getSVLObject(pUsers[i]));
            }

            this._svlObject.setValue("USERS", list);
        }
    }

    public void reset() {
        this.setUsers((UsersBeanOut[])null);
    }

    protected void setSVLObject(SVLObject pObject) throws Exception {
        if (null == pObject) {
            this.reset();
            this._svlObject = ExchangeFormatFactory.instance().createSVLObject();
        } else {
            if (pObject.isAttributeSet("USERS")) {
                SVLObjectList svlList = pObject.getSVLObjectList("USERS");
                UsersBeanOut[] list = UsersBeanOut.createList(svlList);
                this.setUsers(list);
            }

            super.setSVLObject(pObject);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("UserReadBeanOut {");
        if (null != this._users) {
            sb.append("_users=" + this._users);
        }

        sb.append("}");
        return sb.toString();
    }
}
