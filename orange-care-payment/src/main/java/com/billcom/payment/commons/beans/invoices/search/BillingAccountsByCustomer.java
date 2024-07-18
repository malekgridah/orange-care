package com.billcom.payment.commons.beans.invoices.search;

import lombok.Data;

import java.util.List;

@Data
public class BillingAccountsByCustomer {
    private Long customerId;
    private String customerCode;
    private List<InvoicesByBillingAccount> customers;
}
