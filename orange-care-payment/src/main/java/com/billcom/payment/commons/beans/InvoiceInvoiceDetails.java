package com.billcom.payment.commons.beans;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceInvoiceDetails {

	protected BigDecimal amountToPay;
	protected BigDecimal billedAmount;
	protected String expectedPaymentDate;
	protected BigDecimal orderNumber;
	protected String referenceNumber;
	protected String sentDate;
	protected String status;
	protected String invoiceType;
	protected Long documentId;

}
