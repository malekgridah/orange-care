package com.billcom.payment.commons.beans;

import lombok.Data;

@Data
public class InvoicesBean {

	private CustomerReference custRef;
	private BillingAccount baRef;
	private String startDate ;
	private String endDate;
	private Long trsId;
	private String refFacture;

}
