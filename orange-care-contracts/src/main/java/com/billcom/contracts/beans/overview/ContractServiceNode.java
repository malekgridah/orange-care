package com.billcom.contracts.beans.overview;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContractServiceNode {
    private String service;
    private String value;
    private String status;
    private LocalDateTime validFrom;
    private String pendingStatus;
    private Double oneTimeCharge;
    private Double recurringCharge;
    private String paymentOption;
    private String resource;
    private ContractServiceNode[] children;
}
