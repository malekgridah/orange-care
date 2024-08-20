package com.billcom.customers.beans.create;

import lombok.Data;

@Data
public class CreateCustomerRequest {
    private Address address;
    private PaymentArrangement paymentArrangement;
    private Long csDealerId;
    private String prgCode;
    private Long rpCode;
    private String rpCodePub;
    private Long rsCode;
    private Long wpCode;
    private Long custCatCode;
    private String csStatus;
    private Long costId;
    private String csBillcycle;
    private Boolean csDunning;

}
