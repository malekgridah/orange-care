package com.lhs.ws.beans;


import com.lhs.ccb.common.soi.*;

import java.io.Serializable;

public class SVLObjectBean extends BaseSOIBean implements Serializable {
    private NamedValueBean[] _namedValues;

    public SVLObjectBean() {
    }

    public SVLObjectBean(SVLObject var1) throws Exception {
        this.setSVLObject(var1);
        if (var1 != null) {
            String[] var2 = var1.getAttributeNames();
            this._namedValues = new NamedValueBean[var2.length];

            for(int var3 = 0; var3 < var2.length; ++var3) {
                SVLType var4 = var1.getSVLType(var2[var3]);
                NamedValueBean var5 = new NamedValueBean();
                if (SVLType.SVL_DATE.equals(var4)) {
                    DateBean var6 = new DateBean((SVLDate)var1.getValue(var2[var3]));
                    var5.setName(var2[var3]);
                    var5.setValue(var6);
                    this._namedValues[var3] = var5;
                } else if (SVLType.SVL_DATETIME.equals(var4)) {
                    DateTimeBean var7 = new DateTimeBean((SVLDateTime)var1.getValue(var2[var3]));
                    var5.setName(var2[var3]);
                    var5.setValue(var7);
                    this._namedValues[var3] = var5;
                } else if (SVLType.SVL_MONEY.equals(var4)) {
                    MoneyBean var8 = new MoneyBean((SVLMoney)var1.getValue(var2[var3]));
                    var5.setName(var2[var3]);
                    var5.setValue(var8);
                    this._namedValues[var3] = var5;
                } else if (SVLType.SVL_LIST.equals(var4)) {
                    SVLObjectBean var9 = new SVLObjectBean((SVLObject)var1.getValue(var2[var3]));
                    var5.setName(var2[var3]);
                    var5.setValue(var9);
                    this._namedValues[var3] = var5;
                } else if (SVLType.SVL_LISTLIST.equals(var4)) {
                    SVLObjectListBean var10 = new SVLObjectListBean((SVLObjectList)var1.getValue(var2[var3]));
                    var5.setName(var2[var3]);
                    var5.setValue(var10);
                    this._namedValues[var3] = var5;
                } else {
                    SimpleTypeBean var11 = new SimpleTypeBean(var1.getValue(var2[var3]));
                    var5.setName(var2[var3]);
                    var5.setValue(var11);
                    this._namedValues[var3] = var5;
                }
            }
        }

    }

    public NamedValueBean[] getNamedValues() {
        return this._namedValues;
    }

    public void setNamedValues(NamedValueBean[] var1) {
        this._namedValues = var1;
        if (var1 != null) {
            for(int var2 = 0; var2 < var1.length; ++var2) {
                var1[var2].setBeanValue(this._svlObject);
            }

        }
    }

    public void setBeanValue(String var1, SVLObject var2) {
        var2.setValue(var1, this._svlObject);
    }
}
