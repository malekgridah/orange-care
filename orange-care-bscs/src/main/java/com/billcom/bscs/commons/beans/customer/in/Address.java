package com.billcom.bscs.commons.beans.customer.in;

import com.billcom.customer.handling.DateBean;
import com.billcom.customer.handling.DateTimeBean;
import lombok.Data;

@Data
public class Address {
    private DateBean adrBirthdt;
    private String adrCity;
    private String adrCounty;
    private String adrCusttype;
    private String adrDrivelicence;
    private String adrEmail;
    private String adrFname;
    private String adrIdno;
    private String adrLname;
    private Long adrNationality;
    private String adrNationalityPub;
    private String adrPhn1;
    private String adrRemark;
    private Long adrSeq;
    private String adrSex;
    private String adrSmsno;
    private String adrState;
    private String adrStreet;
    private String adrStreetno;
    private DateTimeBean adrValiddate;
    private String adrZip;
    private Long countryId;
    private String countryIdPub;
    private Long idtypeCode;
    private Long lngCode;
    private String lngCodePub;
    private Long ttlId;
    private String ttlIdPub;
}
