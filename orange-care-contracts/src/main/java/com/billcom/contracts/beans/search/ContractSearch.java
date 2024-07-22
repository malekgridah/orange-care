package com.billcom.contracts.beans.search;

import lombok.Data;

@Data
public class ContractSearch {
    private Long coId;
    private String coIdPub;
    private String csCode;
    private String publicKey;
    private Integer status;
    private String subMarket;
    private Long subMarketId;
    private String rateplan;
    private Long rpCode;
    private String homeNetwork;
    private String customer;
    private String street;
    private String city;

    private String resType;
    private String resNo;
}
