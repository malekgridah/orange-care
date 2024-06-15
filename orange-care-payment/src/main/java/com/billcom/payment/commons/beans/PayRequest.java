package com.billcom.payment.commons.beans;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class PayRequest {

	@XmlElement(name = "PayBean", required = true)
	private PayBean payBean;

	public PayRequest() {
	}

}
