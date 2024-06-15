package com.billcom.payment.commons.beans;

import lombok.Data;

@Data
public class BaseWSResponse {

	private String errorCode;
	private String comment;
	private Boolean isSuccessful;
}
