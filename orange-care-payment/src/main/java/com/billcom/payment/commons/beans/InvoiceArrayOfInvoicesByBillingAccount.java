package com.billcom.payment.commons.beans;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class InvoiceArrayOfInvoicesByBillingAccount {
	
	 protected List<InvoiceInvoicesByBillingAccount> item;

	 public List<InvoiceInvoicesByBillingAccount> getItem() {
		 if (item == null) {
			 item = new ArrayList<>();
		 }
		 return this.item;
	 }

}
