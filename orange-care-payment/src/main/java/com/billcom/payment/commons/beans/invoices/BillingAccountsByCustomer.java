package com.billcom.payment.commons.beans.invoices;

import lombok.Data;

import java.util.List;

@Data
public class BillingAccountsByCustomer {
    private String customerId;
    private String customerCode;
    private List<InvoicesByBillingAccount> billingAccounts;
}
