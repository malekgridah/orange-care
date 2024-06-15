package com.billcom.payment.commons.enums;

import lombok.Getter;

@Getter
public enum UseCasesIdPub {
    PAYMENT("PAYMENT","WRITE"),
    RV_PAYMENT("PAYMENT","REVERSE"),
    DEPOSIT("DEPOSIT","WRITE"),
    RV_DEPOSIT("DEPOSIT","REVERSE"),
    PAYMENT_REFUND("PAYMENTREFUND","WRITE");

    private final String value;
    private final String action;

    UseCasesIdPub(String value, String action) {
        this.value = value;
        this.action = action;
    }

}
