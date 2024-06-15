package com.billcom.payment.commons.beans;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class PayBean {

	@XmlElement(required = true)
	private String useCaseIdPub;

	private boolean simulation;

	@XmlElement(required = true)
	private String payChannelIdPub;
	private Money amount;
	private Customer customer;
	private BillingAccount billingAccount;
	private DateTime referenceDate;

	@XmlElement(required = true)
	private String referenceKey;

	@XmlElement(required = true)
	private Document document;

	@XmlElement(required = true)
	private String payMethodIdPub;

	private String validThruDate;
	private String remark;

	//bank attributes
	private String accountNo;
	private String accountOwner;
	private String bankName;
	private String bankCode;

	//GPS attributes
	private String entityCode;
	private String entityName;
	private String usernameGPS;

	private String glAccountCash;
	private String glAccountDis;

	private Long trsId;
	private String operation;
}
