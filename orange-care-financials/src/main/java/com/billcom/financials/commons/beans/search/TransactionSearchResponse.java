package com.billcom.financials.commons.beans.search;

import com.billcom.financials.commons.OperationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransactionSearchResponse extends OperationResponse {
    private List<TransactionSearch> transactions;
}
