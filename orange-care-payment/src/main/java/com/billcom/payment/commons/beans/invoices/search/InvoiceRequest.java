package com.billcom.payment.commons.beans.invoices.search;

import com.billcom.payment.commons.beans.BillingAccount;
import com.billcom.payment.commons.beans.Customer;
import com.billcom.payment.commons.beans.Document;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceRequest {

    private Document document;
    private Customer customer;
    private BillingAccount billingAccount;
    private String dirNum;
    private String cin;
    private String registryNumber;
    private Long searchCount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String prgCodeInclude;
    private String prgCodeExclude;
}
