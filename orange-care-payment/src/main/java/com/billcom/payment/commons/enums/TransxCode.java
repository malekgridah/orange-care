package com.billcom.payment.commons.enums;

import lombok.Getter;

@Getter
public enum TransxCode {
    CE2CO("CE2CO"),
    RV_CE2CO("RV-CE2CO"),
    CE2IN_X2("CE2IN-X2"),
    RV_CE2IN("RV-CE2IN"),
    RF_CE2IN("RF-CE2IN"),
    CE2DP("CE2DP"),
    RV_CE2DP("RV-CE2DP");


    private final String value;

    TransxCode(String value) {
        this.value = value;
    }
}
