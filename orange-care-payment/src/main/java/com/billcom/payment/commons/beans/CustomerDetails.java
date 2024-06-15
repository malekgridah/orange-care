package com.billcom.payment.commons.beans;

import lombok.Data;

import java.util.List;

@Data
public class CustomerDetails {

	private Long customerId;
	private String prgcode;
	private String regNo;
	private String cin;
	private List<Long> customerIds;
//	private List<CustomerPrgCode> customerPrgCodes;

}
