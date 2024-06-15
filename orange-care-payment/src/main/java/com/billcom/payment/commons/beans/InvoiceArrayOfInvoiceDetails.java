package com.billcom.payment.commons.beans;

import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
public class InvoiceArrayOfInvoiceDetails {

	protected List<InvoiceInvoiceDetails> item;

	public List<InvoiceInvoiceDetails> getItem() {
		if (item == null) {
			item = new ArrayList<>();
		}

		return this.item;
	}

}
