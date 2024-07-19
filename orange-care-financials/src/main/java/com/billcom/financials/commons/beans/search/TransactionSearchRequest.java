package com.billcom.financials.commons.beans.search;

import lombok.Data;
import org.joda.time.DateTime;
import java.util.List;

@Data
public class TransactionSearchRequest {
    private Long transactionId;
    private String transactionReference;

    private Long csId;
    private String csIdPub;

    private Long billingAccountId;
    private String billingAccountCode;

    private DateTime dateFrom;
    private DateTime dateUntil;

    private List<String> transactionCodes;
    private String transactionStatus;

    private Long searchCount;
    private String orderBy;


}
