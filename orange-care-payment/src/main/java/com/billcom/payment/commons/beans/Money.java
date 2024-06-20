package com.billcom.payment.commons.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Money {

    private String currency;
    private Double amount;

}
