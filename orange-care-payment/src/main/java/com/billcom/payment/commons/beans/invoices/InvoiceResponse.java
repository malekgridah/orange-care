package com.billcom.payment.commons.beans.invoices;

import com.billcom.payment.commons.beans.BaseWSResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class InvoiceResponse extends BaseWSResponse {
    private List<BillingAccountsByCustomer> customers;
}
