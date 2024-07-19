package com.billcom.financials.commons.beans.search;

import com.billcom.financials.commons.Money;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionSearch {
    private Long transactionId;
    private String transactionReference;
    private String transactionCodePub;
    private String transactionType;

    private Long csId;
    private String csIdPub;

    private LocalDateTime entryDate;
    private LocalDateTime referenceDate;

    private Money cashPayAmount;
    private Money currentAmount;

    private String glAccount;
    private Long paymentMethodId;

}
