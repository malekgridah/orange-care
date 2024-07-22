package com.billcom.customers.beans.overview;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CsOverviewAddress {
    private Long adrSeq;
    private Long ttlId;
    private String adrLName;
    private String adrEmail;
    private String adrFame;
    private String adrStreet;
    private String adrCity;
    private String adrZip;
    private LocalDate adrBirthDate;
    private String adrNationality;
    private Long countryId;
    private Long docTypeId;
    private String idNo;
}
