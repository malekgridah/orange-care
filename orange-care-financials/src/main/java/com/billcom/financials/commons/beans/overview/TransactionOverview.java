package com.billcom.financials.commons.beans.overview;

import com.billcom.financials.commons.Money;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionOverview {
    private Long transactionId;
    private String transactionReference;
    private String transactionCodePub;
    private String useCaseCode;

    private Long csId;
    private String csIdPub;

    private Money amountCashPay;
    private Money amountCurrentPay;

    private LocalDateTime entryDate;
    private LocalDateTime referenceDate;
    private LocalDateTime effectiveDate;

    private Long glAccount;
    private String user;
    private String paymentChannel;
    private String payMethodIdPub;

    private Boolean reversalFlag;
    private Boolean reversed;
    private Long revOrigTransactionId;

    private TransactionOverviewDetails details;
}
