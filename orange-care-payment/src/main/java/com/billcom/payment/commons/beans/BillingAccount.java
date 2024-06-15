package com.billcom.payment.commons.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BillingAccount {

    private Long billingAccountId;
    private String billingAccountCode;
}
