package com.billcom.payment.clients.soap.sms;

import lombok.Data;

@Data
public class SendNotificationRequest {

    private String msisdn;
    private String service;
    private String smsText;
    private String language;
    private String canal;

}
