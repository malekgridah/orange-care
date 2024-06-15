package com.billcom.payment.commons.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class InvoicesCustomerReferencesRequest extends InvoiceRequest {
	private List<Long> customerIds;

}
