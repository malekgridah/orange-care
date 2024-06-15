package com.lhs.ws.beans;

import com.lhs.ccb.common.soi.*;

import java.io.Serializable;
import java.util.Date;

public class BeanSOIConverter implements Serializable {
    public static final char CHAR_NULL_VALUE = ' ';
    public static final Character CHAR_NULL_OBJECT = Character.valueOf(' ');
    public static final Date DATE_NULL_VALUE = new Date(0L);

    public BeanSOIConverter() {
    }

    public static boolean isNull(char var0) {
        return ' ' == var0;
    }

    public static boolean isNull(Date var0) {
        return null == var0 || 0L == var0.getTime();
    }

    public static void setValue(String var0, char var1, SVLObject var2) {
        if (isNull(var1)) {
            var2.setValue(var0, SVLNullConversion.CHAR_NULL_VALUE);
        } else {
            var2.setValue(var0, var1);
        }

    }

    public static void setValue(String var0, Date var1, SVLObject var2, SVLType var3) {
        if (isNull(var1)) {
            var2.setValue(var0, SVLNullConversion.getNullObject(var3));
        } else {
            if (SVLType.SVL_DATE.equals(var3)) {
                SVLDate var4 = ExchangeFormatFactory.instance().createSVLDate();
                var4.setDate(var1);
                var2.setValue(var0, var4);
            }

            if (SVLType.SVL_DATETIME.equals(var3)) {
                SVLDateTime var5 = ExchangeFormatFactory.instance().createSVLDateTime();
                var5.setDateTime(var1);
                var2.setValue(var0, var5);
            }
        }

    }

    public static Character getCharValue(String var0, SVLObject var1) {
        Character var2 = (Character)var1.getValue(var0);
        return SVLNullConversion.CHAR_NULL_OBJECT.equals(var2) ? CHAR_NULL_OBJECT : var2;
    }

    public static SVLObject getSVLObject(BaseSOIBean var0) {
        return null == var0 ? null : var0.getSVLObject();
    }

    public static void setSVLObject(BaseSOIBean var0, SVLObject var1) throws Exception {
        if (null != var0) {
            var0.setSVLObject(var1);
        }
    }
}
