package com.billcom.financials.commons.beans.overview;

import com.billcom.financials.commons.Money;
import lombok.Data;

@Data
public class TransactionOverviewDetails {
    private Long documentId;
    private String documentCode;

    private String glAccountRevenue;

    private Money amountCashPay;
    private Money amountPay;
    private Money amountGrossPay;
    private Money taxAmountPay;
}
