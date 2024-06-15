package com.billcom.bscs.commons.beans.customer.in;

import com.billcom.customer.handling.MoneyBean;
import lombok.Data;

@Data
public class PaymentArrangement {

    protected String cspAccno;
    protected String cspAccowner;
    protected Boolean cspActUsed;
    protected String cspBankcity;
    protected String cspBankcode;
    protected Long cspBankcountry;
    protected String cspBankcountryPub;
    protected String cspBankcounty;
    protected String cspBankname;
    protected String cspBankstate;
    protected String cspBankstreet;
    protected String cspBankstreetno;
    protected String cspBankzip;
    protected Long cspCcagCode;
    protected String cspCcagCodePub;
    protected String cspCcvaliddt;
    protected MoneyBean cspCeiling;
    protected Boolean cspDeleted;
    protected String cspOrdernumber;
    protected Long cspPmntId;
    protected String cspPmntIdPub;
    protected Long cspSeqno;
    protected String cspSwiftcode;
}
