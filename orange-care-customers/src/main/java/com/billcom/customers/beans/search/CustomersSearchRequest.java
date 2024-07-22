package com.billcom.customers.beans.search;


import lombok.Data;

@Data
public class CustomersSearchRequest {
    String csStatus;
    String adrLname;
    String adrFname;
    Integer srchCount;
    Long startIndex;
    Boolean paymentResp;
    Boolean csContrResp;
    Boolean flagCase;
    Boolean flagMatchcode;
    Boolean includeResHist;
    String adrIdno;
    String csCode;
    String csIdPub;
    String resType;
    String resNo;
}
