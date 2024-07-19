package com.billcom.financials.commons.beans.overview;

import com.billcom.financials.commons.OperationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionOverviewResponse extends OperationResponse {
    private TransactionOverview transactionOverview;
}
