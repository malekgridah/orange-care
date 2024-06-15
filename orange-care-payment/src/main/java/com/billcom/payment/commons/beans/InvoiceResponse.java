package com.billcom.payment.commons.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InvoiceResponse extends BaseWSResponse {

    protected CustomerReference customerRef;
    protected InvoiceArrayOfInvoicesByBillingAccount invoicesByBa;

}
