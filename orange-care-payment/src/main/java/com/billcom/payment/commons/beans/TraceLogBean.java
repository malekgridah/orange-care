package com.billcom.payment.commons.beans;

import lombok.Data;

import java.util.Date;

@Data
public class TraceLogBean {

	private String user;
	private String platform;
	private String operation;
	private String status;
	private String Description;
	private String errorCode ;
	private Long trsId;
	private String rechargeId;
	private String amount;
	private String refFacture; 
	private Date date;

}
