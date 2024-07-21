package com.billcom.financials.utils;

import com.billcom.financials.commons.Money;
import org.joda.time.DateTime;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class FMSConvertor {

    public static Money toMoney(Double amount, String currency) {
        Money money = new Money();
        if (amount != null) money.setAmount(amount);
        if (currency != null) money.setCurrency(currency);
        return money;
    }

    public static XMLGregorianCalendar toXMLGregorianCalendar(DateTime dateTime) {
        try {
            return DatatypeFactory
                    .newInstance()
                    .newXMLGregorianCalendar(dateTime.toGregorianCalendar());
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

}
