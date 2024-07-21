package com.billcom.financials.commons.beans.document;

import com.billcom.financials.commons.Money;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DocumentTransaction {
    private Long transactionId;
    private String transactionReference;
    private String transCodePub;
    private String useCaseCode;

    private Money amountCashPay;
    private Money amountCurrentPay;
    private Money amountCurrentDoc;

    private LocalDate entryDate;
    private LocalDate refDate;

    private String itemCreditDebitType;
}
