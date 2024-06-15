package com.lhs.ws.beans;

import com.lhs.ccb.common.soi.SVLDateTime;
import com.lhs.ccb.common.soi.SVLNullConversion;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class DateTimeBean extends BaseSOIBean implements Serializable {
    private Calendar _date;

    public DateTimeBean() {
    }

    public DateTimeBean(SVLDateTime var1) {
        if (null == var1) {
            this._date = null;
        } else {
            this._date = Calendar.getInstance();
            Date var2 = var1.getDateTime();
            if (SVLNullConversion.TIMESTAMP_LONG_NULL_VALUE == var2.getTime()) {
                this._date.setTime(BeanSOIConverter.DATE_NULL_VALUE);
            } else {
                this._date.setTime(var2);
            }
        }

    }

    public Calendar getDate() {
        return this._date;
    }

    public void setDate(Calendar var1) {
        this._date = var1;
    }

    public String toString() {
        return "" + this._date;
    }
}
