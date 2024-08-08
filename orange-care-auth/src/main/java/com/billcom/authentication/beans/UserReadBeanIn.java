package com.billcom.authentication.beans;

import com.lhs.ccb.common.soi.ExchangeFormatFactory;
import com.lhs.ccb.common.soi.SVLObject;
import com.lhs.ccb.common.soi.SVLObjectList;
import com.lhs.ws.beans.BaseSOIBean;
import com.lhs.ws.beans.BeanSOIConverter;

import java.io.Serializable;

public class UserReadBeanIn extends BaseSOIBean implements Serializable {
    private String _name;
    private RequestedUsersBeanIn[] _requestedUsers;
    private RequestedGroupsBeanIn[] _requestedGroups;

    public UserReadBeanIn() {
    }

    public UserReadBeanIn(SVLObject pObject) throws Exception {
        this.setSVLObject(pObject);
    }

    public static UserReadBeanIn[] createList(int size) {
        UserReadBeanIn[] list = new UserReadBeanIn[size];
        return list;
    }

    public static UserReadBeanIn[] createList(SVLObjectList pList) throws Exception {
        if (null == pList) {
            return null;
        } else {
            int size = pList.size();
            UserReadBeanIn[] list = new UserReadBeanIn[size];

            for(int indx = 0; indx < pList.size(); ++indx) {
                list[indx] = new UserReadBeanIn(pList.get(indx));
            }

            return list;
        }
    }

    public String getName() {
        return this._name;
    }

    public void setName(String pName) {
        this._name = pName;
        if (null == pName) {
            this._svlObject.removeAttribute("NAME");
        } else {
            this._svlObject.setValue("NAME", pName);
        }
    }

    public RequestedUsersBeanIn[] getRequestedUsers() {
        return this._requestedUsers;
    }

    public void setRequestedUsers(RequestedUsersBeanIn[] pRequestedUsers) {
        this._requestedUsers = pRequestedUsers;
        if (null == pRequestedUsers) {
            this._svlObject.removeAttribute("REQUESTED_USERS");
        } else {
            SVLObjectList list = ExchangeFormatFactory.instance().createSVLObjectList();

            for(int i = 0; i < pRequestedUsers.length; ++i) {
                list.add(BeanSOIConverter.getSVLObject(pRequestedUsers[i]));
            }

            this._svlObject.setValue("REQUESTED_USERS", list);
        }
    }

    public RequestedGroupsBeanIn[] getRequestedGroups() {
        return this._requestedGroups;
    }

    public void setRequestedGroups(RequestedGroupsBeanIn[] pRequestedGroups) {
        this._requestedGroups = pRequestedGroups;
        if (null == pRequestedGroups) {
            this._svlObject.removeAttribute("REQUESTED_GROUPS");
        } else {
            SVLObjectList list = ExchangeFormatFactory.instance().createSVLObjectList();

            for(int i = 0; i < pRequestedGroups.length; ++i) {
                list.add(BeanSOIConverter.getSVLObject(pRequestedGroups[i]));
            }

            this._svlObject.setValue("REQUESTED_GROUPS", list);
        }
    }

    public void reset() {
        this.setName((String)null);
        this.setRequestedUsers((RequestedUsersBeanIn[])null);
        this.setRequestedGroups((RequestedGroupsBeanIn[])null);
    }

    protected void setSVLObject(SVLObject pObject) throws Exception {
        if (null == pObject) {
            this.reset();
            this._svlObject = ExchangeFormatFactory.instance().createSVLObject();
        } else {
            if (pObject.isAttributeSet("NAME")) {
                Object s = pObject.getValue("NAME");
                if (s == null) {
                    this.setName((String)null);
                } else {
                    this.setName(s.toString());
                }
            }

            SVLObjectList svlList;
            if (pObject.isAttributeSet("REQUESTED_USERS")) {
                svlList = pObject.getSVLObjectList("REQUESTED_USERS");
                RequestedUsersBeanIn[] list = RequestedUsersBeanIn.createList(svlList);
                this.setRequestedUsers(list);
            }

            if (pObject.isAttributeSet("REQUESTED_GROUPS")) {
                svlList = pObject.getSVLObjectList("REQUESTED_GROUPS");
                RequestedGroupsBeanIn[] list = RequestedGroupsBeanIn.createList(svlList);
                this.setRequestedGroups(list);
            }

            super.setSVLObject(pObject);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("UserReadBeanIn {");
        if (null != this._name) {
            sb.append("_name=" + this._name);
        }

        if (null != this._requestedUsers) {
            sb.append("_requestedUsers=" + this._requestedUsers);
        }

        if (null != this._requestedGroups) {
            sb.append("_requestedGroups=" + this._requestedGroups);
        }

        sb.append("}");
        return sb.toString();
    }
}

