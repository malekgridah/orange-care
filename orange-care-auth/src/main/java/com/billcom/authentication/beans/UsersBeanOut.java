package com.billcom.authentication.beans;

import com.lhs.ccb.common.soi.ExchangeFormatFactory;
import com.lhs.ccb.common.soi.SVLObject;
import com.lhs.ccb.common.soi.SVLObjectList;
import com.lhs.ws.beans.BaseSOIBean;
import java.io.Serializable;

public class UsersBeanOut extends BaseSOIBean implements Serializable {
    private String _name;
    private String _description;
    private Long _lngCode;
    private String _lngShdes;
    private Boolean _passwordExpired;
    private String _userType;
    private String _firstName;
    private String _lastName;
    private RoleBeanOut[] _roles;

    public UsersBeanOut() {
    }

    public UsersBeanOut(SVLObject pObject) throws Exception {
        this.setSVLObject(pObject);
    }

    public static UsersBeanOut[] createList(int size) {
        UsersBeanOut[] list = new UsersBeanOut[size];
        return list;
    }

    public static UsersBeanOut[] createList(SVLObjectList pList) throws Exception {
        if (null == pList) {
            return null;
        } else {
            int size = pList.size();
            UsersBeanOut[] list = new UsersBeanOut[size];

            for(int indx = 0; indx < pList.size(); ++indx) {
                list[indx] = new UsersBeanOut(pList.get(indx));
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

    public String getDescription() {
        return this._description;
    }

    public void setDescription(String pDescription) {
        this._description = pDescription;
        if (null == pDescription) {
            this._svlObject.removeAttribute("DESCRIPTION");
        } else {
            this._svlObject.setValue("DESCRIPTION", pDescription);
        }
    }

    public String getLastName() {
        return this._lastName;
    }

    public void setLastName(String pLastName) {
        this._lastName = pLastName;
        if (null == pLastName) {
            this._svlObject.removeAttribute("LAST_NAME");
        } else {
            this._svlObject.setValue("LAST_NAME", pLastName);
        }
    }

    public String getFirstName() {
        return this._firstName;
    }

    public void setFirstName(String pFirstName) {
        this._firstName = pFirstName;
        if (null == pFirstName) {
            this._svlObject.removeAttribute("FIRST_NAME");
        } else {
            this._svlObject.setValue("FIRST_NAME", pFirstName);
        }
    }

    public RoleBeanOut[] getRoles() {
        return this._roles;
    }

    public void setRoles(RoleBeanOut[]  pRoles) {
        this._roles = pRoles;
        if (null == pRoles) {
            this._svlObject.removeAttribute("ROLES");
        } else {
            this._svlObject.setValue("ROLES", pRoles);
        }
    }

    public Long getLngCode() {
        return this._lngCode;
    }

    public void setLngCode(Long pLngCode) {
        this._lngCode = pLngCode;
        if (null == pLngCode) {
            this._svlObject.removeAttribute("LNG_CODE");
        } else {
            this._svlObject.setValue("LNG_CODE", pLngCode);
        }
    }

    public String getLngShdes() {
        return this._lngShdes;
    }
    public void setLngShdes(String pLngShdes) {
        this._lngShdes = pLngShdes;
        if (null == pLngShdes) {
            this._svlObject.removeAttribute("LNG_SHDES");
        } else {
            this._svlObject.setValue("LNG_SHDES", pLngShdes);
        }
    }

    public Boolean getPasswordExpired() {
        return this._passwordExpired;
    }

    public void setPasswordExpired(Boolean pPasswordExpires) {
        this._passwordExpired = pPasswordExpires;
        if (null == pPasswordExpires) {
            this._svlObject.removeAttribute("PASSWORD_EXPIRED");
        } else {
            this._svlObject.setValue("PASSWORD_EXPIRED", pPasswordExpires);
        }
    }

    public String getUserType() {
        return this._userType;
    }

    public void setUserType(String pUserType) {
        this._userType = pUserType;
        if (null == pUserType) {
            this._svlObject.removeAttribute("USER_TYPE");
        } else {
            this._svlObject.setValue("USER_TYPE", pUserType);
        }
    }

    public void reset() {
        this.setName(null);
        this.setDescription(null);
        this.setLastName(null);
        this.setFirstName(null);
        this.setRoles(null);
        this.setLngCode(null);
        this.setLngShdes(null);
        this.setPasswordExpired(null);
        this.setUserType(null);
    }

    protected void setSVLObject(SVLObject pObject) throws Exception {
        if (null == pObject) {
            this.reset();
            this._svlObject = ExchangeFormatFactory.instance().createSVLObject();
        } else {
            Object s;
            if (pObject.isAttributeSet("NAME")) {
                s = pObject.getValue("NAME");
                if (s == null) {
                    this.setName((String)null);
                } else {
                    this.setName(s.toString());
                }
            }

            if (pObject.isAttributeSet("DESCRIPTION")) {
                s = pObject.getValue("DESCRIPTION");
                if (s == null) {
                    this.setDescription((String)null);
                } else {
                    this.setDescription(s.toString());
                }
            }

            if (pObject.isAttributeSet("LAST_NAME")) {
                s = pObject.getValue("LAST_NAME");
                if (s == null) {
                    this.setLastName((String)null);
                } else {
                    this.setLastName(s.toString());
                }
            }

            if (pObject.isAttributeSet("FIRST_NAME")) {
                String obj = (String)pObject.getValue("FIRST_NAME");
                this.setFirstName(obj);
            }

            Long obj;
            if (pObject.isAttributeSet("ROLES")) {
                SVLObjectList svlList = pObject.getSVLObjectList("ROLES");
                RoleBeanOut[] list = RoleBeanOut.createList(svlList);
                this.setRoles(list);
            }

            if (pObject.isAttributeSet("LNG_CODE")) {
                obj = (Long)pObject.getValue("LNG_CODE");
                this.setLngCode(obj);
            }

            if (pObject.isAttributeSet("LNG_SHDES")) {
                s = pObject.getValue("LNG_SHDES");
                if (s == null) {
                    this.setLngShdes((String)null);
                } else {
                    this.setLngShdes(s.toString());
                }
            }

            if (pObject.isAttributeSet("PASSWORD_EXPIRED")) {
                Boolean obje = (Boolean) pObject.getValue("PASSWORD_EXPIRED");
                this.setPasswordExpired(obje);
            }

            if (pObject.isAttributeSet("USER_TYPE")) {
                s = pObject.getValue("USER_TYPE");
                if (s == null) {
                    this.setUserType((String)null);
                } else {
                    this.setUserType(s.toString());
                }
            }

            super.setSVLObject(pObject);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("UsersBeanOut {");
        if (null != this._name) {
            sb.append("_name=" + this._name);
        }

        if (null != this._description) {
            sb.append("_description=" + this._description);
        }

        if (null != this._lastName) {
            sb.append("_group=" + this._lastName);
        }

        if (null != this._roles) {
            sb.append("_roles=" + this._roles);
        }

        if (null != this._firstName) {
            sb.append("_isBatch=" + this._firstName);
        }

        if (null != this._lngCode) {
            sb.append("_lngCode=" + this._lngCode);
        }

        if (null != this._lngShdes) {
            sb.append("_lngShdes=" + this._lngShdes);
        }

        if (null != this._passwordExpired) {
            sb.append("_passwordExpired=" + this._passwordExpired);
        }

        if (null != this._userType) {
            sb.append("_userType=" + this._userType);
        }

        sb.append("}");
        return sb.toString();
    }
}
