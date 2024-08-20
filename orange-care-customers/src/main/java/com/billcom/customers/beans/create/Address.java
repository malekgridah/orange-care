package com.billcom.customers.beans.create;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Address {
    private String adrFName;
    private String adrLName;
    private String adrStreet;
    private String adrCity;
    private String adrZip;
    private String countryIdPub;
    private String adrPhone;
    private String adrEmail;
    private String adrCountry;
    private Long ttlId;
    private Long idTypeCode;
    private String adrStreetNo;
    private String adrCustType;
    private String adrNationalityPub;
    private String adrIdNo;
    private Long adrSeq;
    private String masCodePub;
    private String adrSex;
    private LocalDate adrBirthdate;
}
