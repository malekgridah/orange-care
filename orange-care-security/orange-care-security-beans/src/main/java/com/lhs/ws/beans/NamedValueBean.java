package com.lhs.ws.beans;

import com.lhs.ccb.common.soi.ExchangeFormatFactory;
import com.lhs.ccb.common.soi.SVLMoney;
import com.lhs.ccb.common.soi.SVLObject;
import com.lhs.ccb.common.soi.SVLType;

import java.io.Serializable;
import java.util.Date;

public class NamedValueBean extends BaseSOIBean implements Serializable {
    private String _name;
    private BaseSOIBean _value;

    public NamedValueBean() {
    }

    public String getName() {
        return this._name;
    }

    public void setName(String var1) {
        this._name = var1;
    }

    public BaseSOIBean getValue() {
        return this._value;
    }

    public void setValue(BaseSOIBean var1) {
        this._value = var1;
    }

    protected void setBeanValue(SVLObject var1) {
        if (this._value == null) {
            var1.removeAttribute(this._name);
        }

        if (this._value instanceof DateBean) {
            if (((DateBean)this._value).getDate() == null) {
                BeanSOIConverter.setValue(this._name, (Date)null, var1, SVLType.SVL_DATE);
            } else {
                BeanSOIConverter.setValue(this._name, ((DateBean)this._value).getDate().getTime(), var1, SVLType.SVL_DATE);
            }
        } else if (this._value instanceof DateTimeBean) {
            if (((DateTimeBean)this._value).getDate() == null) {
                BeanSOIConverter.setValue(this._name, (Date)null, var1, SVLType.SVL_DATETIME);
            } else {
                BeanSOIConverter.setValue(this._name, ((DateTimeBean)this._value).getDate().getTime(), var1, SVLType.SVL_DATETIME);
            }
        } else if (this._value instanceof MoneyBean) {
            SVLMoney var2 = null;
            if (((MoneyBean)this._value).getAmount() != null) {
                var2 = ExchangeFormatFactory.instance().createSVLMoney();
                var2.setAmount(((MoneyBean)this._value).getAmount());
                var2.setCurrencyCode(((MoneyBean)this._value).getCurrency());
            }

            var1.setValue(this._name, var2);
        } else if (this._value instanceof SimpleTypeBean) {
            var1.setValue(this._name, ((SimpleTypeBean)this._value).getSimpleTypeValue());
        } else if (this._value instanceof SVLObjectBean) {
            var1.setValue(this._name, this._value.getSVLObject());
        } else if (this._value instanceof SVLObjectListBean) {
            var1.setValue(this._name, ((SVLObjectListBean)this._value).getSVLObjectList());
        }

    }
}
