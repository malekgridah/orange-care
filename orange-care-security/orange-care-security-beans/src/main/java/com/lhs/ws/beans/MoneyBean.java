package com.lhs.ws.beans;

import com.lhs.ccb.common.soi.SVLMoney;

import java.io.Serializable;

public class MoneyBean extends BaseSOIBean implements Serializable {
    private String _currency;
    private Double _amount;

    public MoneyBean() {
    }

    public MoneyBean(SVLMoney var1) {
        if (null == var1) {
            this._currency = null;
            this._amount = null;
        } else {
            this._currency = var1.getCurrencyCode();
            this._amount = Double.valueOf(var1.getAmount());
        }

    }

    public String getCurrency() {
        return this._currency;
    }

    public void setCurrency(String var1) {
        this._currency = var1;
    }

    public Double getAmount() {
        return this._amount;
    }

    public void setAmount(Double var1) {
        this._amount = var1;
    }

    public String toString() {
        return "" + this._amount + " (" + this._currency + ")";
    }
}
