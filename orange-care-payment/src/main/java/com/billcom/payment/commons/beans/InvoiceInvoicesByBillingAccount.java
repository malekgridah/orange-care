package com.billcom.payment.commons.beans;

import lombok.Data;

@Data
public class InvoiceInvoicesByBillingAccount {

	protected InvoiceBillingAccountReference baRef;
	protected InvoiceArrayOfInvoiceDetails invoices;

}
