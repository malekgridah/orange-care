package com.billcom.bscs.commons.beans.customer.search;

import lombok.Data;

@Data
public class CustomersSearch {
    private Long csId;
    private String csIdPub;
    private String csCode;
    private String csStatus;
    private String adrLname;
    private String adrFname;
    private String adrStreet;
    private String adrStreetno;
    private String adrZip;
    private String adrCity;
}
