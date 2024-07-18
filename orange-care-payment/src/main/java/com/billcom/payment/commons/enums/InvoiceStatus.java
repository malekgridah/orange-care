package com.billcom.payment.commons.enums;

import lombok.Getter;

@Getter
public enum InvoiceStatus {
    Paid(1,"Paid"),
    Not_Paid(2,"Not_Paid"),
    IN_Progress(3,"IN_Progress"),   ;

    private final Integer statusId;
    private final String status;

    InvoiceStatus(Integer statusId, String status) {
        this.statusId = statusId;
        this.status = status;
    }
}
