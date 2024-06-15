package com.billcom.payment.commons.beans;

import lombok.Data;

@Data
public class Money {

    private String currency;
    private Double amount;

}
