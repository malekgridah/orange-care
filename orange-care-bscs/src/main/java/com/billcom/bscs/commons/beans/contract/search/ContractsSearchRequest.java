package com.billcom.bscs.commons.beans.contract.search;

import lombok.Data;

@Data
public class ContractsSearchRequest {
    private String coStatus;
    private String resType;
    private String coRpCode;
    private String coPaymentOption;

    private String resNo;
    private String coCode;
    private String csLName;
    private String csFName;
    private String csCode;
    private String csIdPub;


    private String market;
    private String subMarket;
    private String network;

    private Integer srchCount;
    private Boolean flagCase;
    private Boolean includeResHist;
}
