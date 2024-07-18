package com.billcom.payment.commons.beans.invoices.search;

import com.billcom.payment.commons.beans.BillingAccount;
import lombok.Data;

import java.util.List;

@Data
public class InvoicesByBillingAccount {
    private BillingAccount billingAccount;
    private List<Invoice> invoices;
}
