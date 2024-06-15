package com.billcom.payment.commons.beans;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class CancelPayRequest {

  @XmlElement(required = true)
  private String handlingReasonIdPub;
  private Long transactionId;
  private Document document;
  private Customer customer;
  private BillingAccount billingAccount;
  private String useCaseIdPub;

}
